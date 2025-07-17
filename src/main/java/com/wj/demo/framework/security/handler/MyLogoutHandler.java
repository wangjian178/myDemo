package com.wj.demo.framework.security.handler;

import com.wj.demo.core.system.service.TokenService;
import com.wj.demo.framework.common.model.LoginUser;
import com.wj.demo.framework.common.utils.SpringContextUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * @ClassName MyLogoutHandler
 * @Description: 登出处理器
 * @Author: W.Jian
 * @CreateDate: 2025/4/25 18:14
 * @Version:
 */
public class MyLogoutHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        TokenService tokenService = SpringContextUtils.getBean(TokenService.class);
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (loginUser != null) {
            tokenService.removeLoginUser(loginUser);
        }
    }
}
