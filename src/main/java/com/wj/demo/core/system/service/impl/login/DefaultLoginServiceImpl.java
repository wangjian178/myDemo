package com.wj.demo.core.system.service.impl.login;

import com.wj.demo.core.system.entity.SysUser;
import com.wj.demo.core.system.enums.UserOnLineStatusEnum;
import com.wj.demo.core.system.enums.UserStatusEnum;
import com.wj.demo.core.system.model.vo.*;
import com.wj.demo.core.system.service.ILoginService;
import com.wj.demo.core.system.service.ISysMenuService;
import com.wj.demo.core.system.service.ISysUserService;
import com.wj.demo.framework.common.constant.BaseConstant;
import com.wj.demo.framework.common.constant.LoginConstant;
import com.wj.demo.framework.common.constant.SecurityConstant;
import com.wj.demo.framework.common.model.LoginUser;
import com.wj.demo.framework.common.property.SystemProperties;
import com.wj.demo.framework.common.utils.CaptchaUtils;
import com.wj.demo.framework.common.utils.JwtUtils;
import com.wj.demo.framework.common.utils.PasswordUtils;
import com.wj.demo.framework.common.utils.SecurityUtils;
import com.wj.demo.framework.exception.exception.BusinessException;
import com.wj.demo.framework.redis.service.RedisClient;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName DefaultSysLoginService
 * @Description: 默认登陆实现
 * @Author: W.Jian
 * @CreateDate: 2025/3/19 16:20
 * @Version:
 */
@Service("ILoginService" + BaseConstant.UNDERLINE + BaseConstant.DEFAULT)
@ConditionalOnExpression("'${system.loginHandler:default}'.equals('default')")
public class DefaultLoginServiceImpl implements ILoginService {

    @Resource
    private SystemProperties systemProperties;

    @Lazy
    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisClient redisClient;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ISysMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.queryByUsername(username);
        if (sysUser == null) {
            throw new BusinessException("500", "用户不存在！");
        }
        if (UserStatusEnum.DISABLE.equals(sysUser.getStatus())) {
            throw new BusinessException("500", "用户已禁用！");
        }
        return createLoginUser(sysUser);
    }

    /**
     * 创建登录用户
     *
     * @param sysUser 用户
     * @return 登录用户
     */
    private UserDetails createLoginUser(SysUser sysUser) {
        LoginUser loginUser = new LoginUser();
        loginUser.setId(sysUser.getId());
        loginUser.setUsername(sysUser.getUsername());
        loginUser.setPassword(sysUser.getPassword());
        //todo 查询用户权限
        Collection<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList("ADMIN");
        return loginUser;
    }

    /**
     * 登录
     *
     * @param loginParamVO 登录参数
     * @return token
     */
    @Override
    public LoginResultVO login(LoginParamVO loginParamVO) {
        // 校验验证码 captcha
        checkCaptcha(loginParamVO.getCaptchaId(), loginParamVO.getCaptchaCode());

        // 检测账号是否锁定
        checkAccountLocked(loginParamVO.getUsername());

        //校验用户是否存在
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginParamVO.getUsername(), loginParamVO.getPassword()));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        //校验用户名密码
        checkPassword(loginParamVO.getPassword(), loginUser.getPassword());

        //校验尝试次数、密码
        checkLoginCount(loginParamVO);

        //生成token
        LoginResultVO loginResultVO = createToken(loginUser);

        //记录token
        redisClient.set(LoginConstant.TOKEN_PREFIX + loginResultVO.getToken(), loginUser, systemProperties.getSecurity().getExpireTime(), TimeUnit.SECONDS);

        //修改登录状态
        sysUserService.updateOnlineStatus(loginUser.getId(), UserOnLineStatusEnum.ONLINE);

        //删除登录记录
        redisClient.delete(LoginConstant.LOGIN_LOCK_USER_RETRY_TIMES_KEY + loginParamVO.getUsername());

        return loginResultVO;
    }

    /**
     * 生成token
     *
     * @param loginUser 用户
     * @return token
     */
    @Override
    public LoginResultVO createToken(LoginUser loginUser) {

        //负载信息
        HashMap<String, String> claims = new HashMap<>();
        claims.put(SecurityConstant.USER_ID, loginUser.getId().toString());
        claims.put(SecurityConstant.USER_NAME, loginUser.getUsername());
        String token = JwtUtils.createToken(claims, systemProperties.getSecurity().getExpireTime(), systemProperties.getSecurity().getSecretKey());

        //记录token
        loginUser.setToken(token);

        //返回token
        LoginResultVO loginResultVO = new LoginResultVO();
        loginResultVO.setToken(token);
        loginResultVO.setExpireTime(systemProperties.getSecurity().getExpireTime());
        loginResultVO.setTimestamp(System.currentTimeMillis());

        return loginResultVO;
    }

    /**
     * 校验登录失败次数
     *
     * @param loginParamVO 登录参数
     */
    private void checkLoginCount(LoginParamVO loginParamVO) {
        //缓存的KEY
        String timesKey = LoginConstant.LOGIN_LOCK_USER_RETRY_TIMES_KEY + loginParamVO.getUsername();
        Integer times = redisClient.get(timesKey);
        if (times == null) {
            times = 0;
        }
        //剩余次数
        Integer restTimes = (times.equals(0) ? BaseConstant.LOCK_USER_MAX_RETRY_TIMES : BaseConstant.LOCK_USER_MAX_RETRY_TIMES - times) - 1;
        //锁定次数加1,并且重置过期时间
        redisClient.set(timesKey, times + 1, BaseConstant.LOCK_USER_LOCK_SECONDS, TimeUnit.SECONDS);

        //超过尝试次数
        if (restTimes.equals(0)) {
            throw new BusinessException("已超过最大尝试次数（" + BaseConstant.LOCK_USER_MAX_RETRY_TIMES + "），账户已锁定！");
        }
    }

    /**
     * 校验验证码
     *
     * @param captchaId   验证码ID
     * @param captchaCode 验证码
     */
    public void checkCaptcha(String captchaId, String captchaCode) {
        if (systemProperties.getSecurity().getCaptcha() == null || !systemProperties.getSecurity().getCaptcha()) {
            return;
        }
        CaptchaUtils.checkCaptcha(captchaId, captchaCode);
    }

    /**
     * 校验用户名密码
     *
     * @param password          原始密码
     * @param encryptedPassword 加密密码
     */
    private void checkPassword(String password, String encryptedPassword) {
        Assert.isTrue(PasswordUtils.match(password, encryptedPassword), "用户名或密码不正确！");
    }

    /**
     * 检测账号是否锁定
     *
     * @param username 用户名
     */
    public void checkAccountLocked(String username) {
        //缓存的KEY
        String timesKey = LoginConstant.LOGIN_LOCK_USER_RETRY_TIMES_KEY + username;

        //  1.获取错误次数
        Integer times = 0;
        Integer cacheTimes = redisClient.get(timesKey);
        if (cacheTimes != null) {
            times = cacheTimes;
        }

        //  2.已锁定提示报错
        if (times.equals(BaseConstant.LOCK_USER_MAX_RETRY_TIMES)) {
            long restLockSeconds = redisClient.getExpire(timesKey);
            throw new BusinessException("用户已锁定！请在" + new BigDecimal(restLockSeconds).divide(new BigDecimal(60), 0, RoundingMode.HALF_UP) + "分钟后重试！");
        }
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @Override
    public UserInfoVO getUserInfo() {
        LoginUser user = SecurityUtils.getUser();
        Assert.notNull(user, "用户未登录！");
        // 用户信息
        SysUserVO sysUserVO = new SysUserVO()
                .setUsername(user.getUsername())
                .setNickname(user.getUsername());
        // 角色信息
        List<SysRoleVO> roleList = new ArrayList<>();
        // 菜单信息
        List<SysMenuVO> menuList = sysMenuService.listAll(null, user.getId());

        return new UserInfoVO()
                .setUser(sysUserVO)
                .setMenuList(menuList)
                .setRoleList(roleList);
    }
}
