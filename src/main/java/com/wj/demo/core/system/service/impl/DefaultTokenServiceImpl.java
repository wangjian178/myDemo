package com.wj.demo.core.system.service.impl;

import com.wj.demo.core.system.config.property.SysConfigProperty;
import com.wj.demo.core.system.service.TokenService;
import com.wj.demo.framework.common.constant.BaseConstant;
import com.wj.demo.framework.common.model.LoginUser;
import com.wj.demo.framework.common.utils.JwtUtils;
import com.wj.demo.framework.common.utils.StringUtils;
import com.wj.demo.framework.redis.service.RedisClient;
import io.jsonwebtoken.Claims;
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
public class DefaultTokenServiceImpl implements TokenService {

    @Resource
    private SysConfigProperty sysConfigProperty;

    @Resource
    private RedisClient redisClient;

    /**
     * 根据request获取token
     *
     * @param request 请求
     * @return token
     */
    @Override
    public String getToken(HttpServletRequest request) {
        String token = request.getHeader(BaseConstant.AUTHORIZATION);
        if (StringUtils.isNotEmpty(token) && token.startsWith(BaseConstant.AUTHORIZATION_PREFIX)) {
            token = token.replace(BaseConstant.AUTHORIZATION_PREFIX, BaseConstant.EMPTY_STRING);
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
        Claims claims = JwtUtils.parseToken(token, sysConfigProperty.getSecretKey());
        String userId = claims.get(BaseConstant.USER_ID, String.class);
        String userKey = BaseConstant.TOKEN_PREFIX + userId;
        LoginUser user = redisClient.get(userKey);
        if (user != null) {
            //todo 刷新token过期时间
        }
        return user;
    }
}
