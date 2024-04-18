package com.wj.demo.baseContext;

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
 * @Desc
 * @date 2024/4/17 10:54
 */
public class BaseContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String langParam = request.getHeader("lang");
        String timeZoneParam = request.getHeader("timeZone");

        Locale locale;
        if (StringUtils.isEmpty(langParam)) {
            locale = Locale.getDefault();
        } else {
            locale = new Locale(langParam.split("_")[0], langParam.split("_")[1]);
        }
        TimeZone timeZone;
        if (StringUtils.isEmpty(timeZoneParam)) {
            timeZone = TimeZone.getDefault();
        } else {
            timeZone = TimeZone.getTimeZone(timeZoneParam);
        }
        BaseContext baseContext = BaseContext
                .build()
                .setLocale(locale)
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
