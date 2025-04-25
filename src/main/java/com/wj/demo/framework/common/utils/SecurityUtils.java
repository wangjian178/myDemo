package com.wj.demo.framework.common.utils;

import com.wj.demo.core.system.config.property.SysConfigProperty;
import com.wj.demo.core.system.service.TokenService;
import com.wj.demo.framework.common.constant.BaseConstant;
import com.wj.demo.framework.common.model.LoginUser;
import com.wj.demo.framework.redis.service.RedisClient;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/25 17:44
 */
public class SecurityUtils {

    /**
     * 获取当前用户鉴权信息
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取用户信息
     *
     * @return 用户
     */
    public static LoginUser getUser() {
        Authentication authentication = getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null && authentication.getPrincipal() instanceof LoginUser loginUser) {
            return loginUser;
        }
        return null;
    }

    /**
     * 获取用户名
     * @return 用户名
     */
    public static String getUsername() {
        LoginUser loginUser = getUser();
        if (loginUser == null) {
            return null;
        }
        return loginUser.getUsername();
    }

    /**
     * 获取用户信息
     *
     * @param token 鉴权信息
     * @return 当前用户
     */
    public static LoginUser getUser(String token) {
        TokenService tokenService =  SpringContextUtils.getBean(TokenService.class);
        return tokenService.getLoginUser(token);
    }
}
