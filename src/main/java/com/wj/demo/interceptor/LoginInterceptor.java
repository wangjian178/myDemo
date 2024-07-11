package com.wj.demo.interceptor;

import com.wj.demo.baseContext.BaseContext;
import com.wj.demo.baseContext.BaseContextHolder;
import com.wj.demo.common.constant.BaseConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author wj
 * @version 1.0
 * @Desc 登录拦截器 鉴权
 * @date 2024/4/17 10:54
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader(BaseConstant.TOKEN);

        //补充上下文信息
        BaseContext baseContext = BaseContextHolder.getBaseContext();
        if (baseContext == null) {
            baseContext = BaseContext.build();
        }
        baseContext.setToken(token);
        BaseContextHolder.setContext(baseContext);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContextHolder.remove();
    }
}
