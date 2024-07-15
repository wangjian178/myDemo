package com.wj.demo.interceptor;

import com.wj.demo.baseContext.BaseContext;
import com.wj.demo.baseContext.BaseContextHolder;
import com.wj.demo.common.constant.BaseConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;
import java.util.TimeZone;

/**
 * @author wj
 * @version 1.0
 * @Desc 国际化拦截器  多语言 时区
 * @date 2024/4/17 10:54
 */
public class BaseContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String langParam = request.getHeader(BaseConstant.LANGUAGE);
        String timeZoneParam = request.getHeader(BaseConstant.TIME_ZONE);

        //时区
        Locale locale = Locale.getDefault();
        if (!StringUtils.isEmpty(langParam)) {
            locale = new Locale(langParam.split("_")[0], langParam.split("_")[1]);
        }

        //语言
        TimeZone timeZone = TimeZone.getDefault();
        if (!StringUtils.isEmpty(timeZoneParam)) {
            timeZone = TimeZone.getTimeZone(timeZoneParam);
        }

        //补充上下文信息
        BaseContext baseContext = BaseContextHolder.getBaseContext();
        if (baseContext == null) {
            baseContext = BaseContext.build();
        }
        baseContext.setLocale(locale)
                .setTimeZone(timeZone);
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
