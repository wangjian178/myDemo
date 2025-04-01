package com.wj.demo.core.system.service.impl.login;

import com.wj.demo.core.system.config.property.SysConfigProperty;
import com.wj.demo.core.system.enums.UserStatusEnum;
import com.wj.demo.core.system.model.vo.LoginParamVO;
import com.wj.demo.core.system.model.vo.LoginResultVO;
import com.wj.demo.core.system.service.ILoginService;
import com.wj.demo.framework.common.constant.BaseConstant;
import com.wj.demo.framework.common.model.User;
import com.wj.demo.framework.common.utils.JwtUtils;
import com.wj.demo.framework.exception.enums.ResultCodeEnum;
import com.wj.demo.framework.exception.exception.BaseException;
import com.wj.demo.framework.redis.service.RedisClient;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName DefaultSysLoginService
 * @Description: 默认登陆实现
 * @Author: W.Jian
 * @CreateDate: 2025/3/19 16:20
 * @Version:
 */
@Service("ILoginService" + BaseConstant.UNDERLINE + BaseConstant.DEFAULT)
public class DefaultLoginServiceImpl implements ILoginService {

    @Resource
    private SysConfigProperty sysConfigProperty;

    @Resource
    private RedisClient redisClient;

    /**
     * 登录
     *
     * @param loginParamVO 登录参数
     * @return token
     */
    @Override
    public LoginResultVO login(LoginParamVO loginParamVO) {
        // 检测账号是否锁定
        checkAccountLocked(loginParamVO.getUsername());

        // 校验验证码 captcha
        checkCaptcha(loginParamVO.getUuid(), loginParamVO.getCaptcha());

        // 查询用户
        User existUser = new User().setId(1L).setUsername(loginParamVO.getUsername()).setPassword(loginParamVO.getPassword());

        //校验密码
        checkLoginPassword(loginParamVO, existUser);

        //生成token
        LoginResultVO loginResultVO = createToken(existUser);

        //记录token
        redisClient.set(BaseConstant.TOKEN_PREFIX + loginResultVO.getToken(), existUser, sysConfigProperty.getExpireTime(), TimeUnit.SECONDS);

        //todo 修改登录状态
        existUser.setStatus(UserStatusEnum.ONLINE);

        //删除登录记录
        redisClient.delete(BaseConstant.LOGIN_LOCK_USER_RETRY_TIMES_KEY + loginParamVO.getUsername());

        return new LoginResultVO();
    }

    /**
     * 生成token
     *
     * @param user 用户
     * @return token
     */
    private LoginResultVO createToken(User user) {

        String token = JwtUtils.createToken(user, sysConfigProperty.getExpireTime());

        LoginResultVO loginResultVO = new LoginResultVO();
        loginResultVO.setToken(token);
        loginResultVO.setExpireTime(sysConfigProperty.getExpireTime());

        return loginResultVO;
    }

    /**
     * 校验登录失败次数
     *
     * @param loginParamVO 登录参数
     * @param existUser    用户
     */
    private void checkLoginPassword(LoginParamVO loginParamVO, User existUser) {
        if (existUser != null) {
            return;
        }
        //缓存的KEY
        String timesKey = BaseConstant.LOGIN_LOCK_USER_RETRY_TIMES_KEY + loginParamVO.getUsername();
        Integer times = redisClient.get(timesKey);
        //剩余次数
        Integer restTimes = (times.equals(0) ? BaseConstant.LOCK_USER_MAX_RETRY_TIMES : BaseConstant.LOCK_USER_MAX_RETRY_TIMES - times) - 1;
        //锁定次数加1,并且重置过期时间
        redisClient.set(timesKey, times + 1, BaseConstant.LOCK_USER_LOCK_SECONDS, TimeUnit.SECONDS);

        //超过尝试次数
        if (restTimes.equals(0)) {
            throw new BaseException("已超过最大尝试次数，账户已锁定！");
        }

        //密码错误
        throw new BaseException("用户名或密码不正确！" + "剩余可重试次数" + restTimes + "次");
    }

    /**
     * 校验验证码
     *
     * @param captcha 验证码
     */
    public void checkCaptcha(String uuid, String captcha) {
        //todo 查询配置是否启用验证码
        boolean captchaEnable = Boolean.FALSE;
        if (!captchaEnable) {
            return;
        }
        String captchaCache = redisClient.get(BaseConstant.LOGIN_CAPTCHA_KEY + uuid);
        if (captchaCache == null) {
            throw new BaseException(ResultCodeEnum.LOGIN_CAPTCHA_EXPIRE_ERROR);
        }
        if (!captcha.equals(captchaCache)) {
            throw new BaseException(ResultCodeEnum.LOGIN_CAPTCHA_ERROR);
        }
    }

    /**
     * 检测账号是否锁定
     *
     * @param username 用户名
     */
    public void checkAccountLocked(String username) {
        //缓存的KEY
        String timesKey = BaseConstant.LOGIN_LOCK_USER_RETRY_TIMES_KEY + username;

        //  1.获取错误次数
        Integer times = 0;
        Integer cacheTimes = redisClient.get(timesKey);
        if (cacheTimes != null) {
            times = cacheTimes;
        }

        //  2.已锁定提示报错
        if (times.equals(BaseConstant.LOCK_USER_MAX_RETRY_TIMES)) {
            long restLockSeconds = redisClient.getExpire(timesKey);
            throw new BaseException("用户已锁定！请在" + new BigDecimal(restLockSeconds).divide(new BigDecimal(60), 0, RoundingMode.HALF_UP) + "分钟后重试！");
        }
    }
}
