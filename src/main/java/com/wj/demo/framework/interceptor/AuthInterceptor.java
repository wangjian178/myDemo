package com.wj.demo.framework.interceptor;

import com.wj.demo.framework.baseContext.BaseContext;
import com.wj.demo.framework.baseContext.BaseContextHolder;
import com.wj.demo.framework.common.constant.BaseConstant;
import com.wj.demo.framework.common.model.User;
import com.wj.demo.framework.common.utils.SecurityUtils;
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


    //用户信息
    User user = SecurityUtils.getUser();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(BaseConstant.TOKEN);

        if (StringUtils.isEmpty(token)) {
            //跳转到登录登录
            response.sendRedirect(BaseConstant.LOGIN_INDEX);
            return false;
        }
        //todo token 过期或者失效
        if (false) {
            //跳转到首页登录
            response.sendRedirect(BaseConstant.LOGIN_INDEX);
            return false;
        }

        //补充上下文信息
        BaseContext baseContext = BaseContextHolder.getBaseContext();
        if (baseContext == null) {
            baseContext = BaseContext.build();
        }
        baseContext.setToken(token);
        baseContext.setUser(user);
        BaseContextHolder.setContext(baseContext);

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
