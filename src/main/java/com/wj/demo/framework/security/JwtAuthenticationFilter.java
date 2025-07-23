package com.wj.demo.framework.security;

import com.wj.demo.core.system.service.ITokenService;
import com.wj.demo.framework.common.model.LoginUser;
import com.wj.demo.framework.common.utils.SecurityUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * @ClassName JwtAuthenticationTokenFilter
 * @Description: jwt token拦截器
 * @Author: W.Jian
 * @CreateDate: 2025/4/15 11:47
 * @Version:
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Resource
    private ITokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        LoginUser loginUser = null;

        try {
            loginUser = this.tokenService.getLoginUser(request);
        } catch (Exception e) {
            log.error("获取当前登录用户信息失败", e);
        }

        if (Objects.nonNull(loginUser) && Objects.isNull(SecurityUtils.getAuthentication())) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails((new WebAuthenticationDetailsSource()).buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
