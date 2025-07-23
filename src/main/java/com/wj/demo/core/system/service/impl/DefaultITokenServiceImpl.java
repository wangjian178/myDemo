package com.wj.demo.core.system.service.impl;

import com.wj.demo.core.system.enums.UserOnLineStatusEnum;
import com.wj.demo.core.system.service.ISysUserService;
import com.wj.demo.core.system.service.ITokenService;
import com.wj.demo.framework.common.constant.LoginConstant;
import com.wj.demo.framework.common.constant.SecurityConstant;
import com.wj.demo.framework.common.model.LoginUser;
import com.wj.demo.framework.common.utils.StringUtils;
import com.wj.demo.framework.redis.service.RedisClient;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

/**
 * @ClassName DefaultTokenServiceImpl
 * @Description: 默认token认证
 * @Author: W.Jian
 * @CreateDate: 2025/4/25 10:03
 * @Version:
 */
@Service
public class DefaultITokenServiceImpl implements ITokenService {

    @Resource
    private RedisClient redisClient;

    @Resource
    private ISysUserService sysUserService;

    /**
     * 根据request获取token
     *
     * @param request 请求
     * @return token
     */
    @Override
    public String getToken(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstant.AUTHORIZATION);
        if (StringUtils.isNotEmpty(token) && token.startsWith(SecurityConstant.AUTHORIZATION_PREFIX)) {
            token = token.replace(SecurityConstant.AUTHORIZATION_PREFIX, SecurityConstant.EMPTY_STRING);
        }
        return token;
    }

    /**
     * 根据request获取用户信息
     *
     * @param request 请求
     * @return 用户信息
     */
    @Override
    public LoginUser getLoginUser(HttpServletRequest request) {
        String token = getToken(request);
        return getLoginUser(token);
    }

    /**
     * 根据token获取用户信息
     *
     * @param token token
     * @return 用户信息
     */
    @Override
    public LoginUser getLoginUser(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        String userKey = LoginConstant.TOKEN_PREFIX + token;
        LoginUser user = redisClient.get(userKey);
        if (user != null) {
            //todo 刷新token过期时间
        }
        return user;
    }

    /**
     * 删除用户信息
     *
     * @param loginUser 用户信息
     */
    @Override
    public void removeLoginUser(LoginUser loginUser) {
        //清除缓存
        String userKey = LoginConstant.TOKEN_PREFIX + loginUser.getToken();
        redisClient.delete(userKey);

        //修改在线状态
        sysUserService.updateOnlineStatus(loginUser.getId(), UserOnLineStatusEnum.OFFLINE);
    }
}
