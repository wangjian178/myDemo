package com.wj.demo.framework.interceptor;

import com.wj.demo.core.system.config.property.SysConfigProperty;
import com.wj.demo.core.system.service.TokenService;
import com.wj.demo.framework.common.model.LoginUser;
import com.wj.demo.framework.common.utils.SecurityUtils;
import com.wj.demo.framework.common.utils.SpringContextUtils;
import com.wj.demo.framework.common.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author wj
 * @version 1.0
 * @Desc 权限拦截器  校验是否登录、过期
 * @date 2024/7/8 15:42
 */
public class AuthInterceptor implements HandlerInterceptor {

    /**
     * 拦截器
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理器
     * @return 是否拦截
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        TokenService tokenService = SpringContextUtils.getBean(TokenService.class);
        String token = tokenService.getToken(request);

        SysConfigProperty sysConfigProperty = SpringContextUtils.getBean(SysConfigProperty.class);
        String loginPage = sysConfigProperty.getLoginPage();

        if (StringUtils.isEmpty(token)) {
            //跳转到登录登录
            response.sendRedirect(loginPage);
            return false;
        }

        LoginUser loginUser = SecurityUtils.getUser(token);
        if (loginUser == null) {
            //未登录或者过期
            //跳转到首页登录
            response.sendRedirect(loginPage);
            return false;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
