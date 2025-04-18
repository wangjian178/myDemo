package com.wj.demo.framework.interceptor;

import com.wj.demo.framework.baseContext.BaseContext;
import com.wj.demo.framework.baseContext.BaseContextHolder;
import com.wj.demo.framework.common.constant.BaseConstant;
import com.wj.demo.framework.common.utils.SecurityUtils;
import com.wj.demo.framework.common.utils.SpringContextUtils;
import com.wj.demo.framework.common.utils.StringUtils;
import com.wj.demo.framework.redis.service.RedisClient;
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
        String token = request.getHeader(BaseConstant.TOKEN);

        if (StringUtils.isEmpty(token)) {
            //跳转到登录登录
            response.sendRedirect(BaseConstant.LOGIN_INDEX);
            return false;
        }

        RedisClient redisClient = SpringContextUtils.getBean(RedisClient.class);
        if (redisClient == null || !redisClient.exists((BaseConstant.TOKEN_PREFIX + token).replace(BaseConstant.AUTHORIZATION_PREFIX, BaseConstant.EMPTY_STRING))) {
            //未登录或者过期
            //跳转到首页登录
            response.sendRedirect(BaseConstant.LOGIN_INDEX);
            return false;
        }

        //补充上下文信息
        BaseContext baseContext = BaseContextHolder.getBaseContext();
        baseContext.setToken(token);
        //用户信息
        baseContext.setUser(SecurityUtils.getUser());

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
