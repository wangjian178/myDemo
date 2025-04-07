package com.wj.demo.framework.interceptor;

import com.wj.demo.framework.baseContext.BaseContext;
import com.wj.demo.framework.baseContext.BaseContextHolder;
import com.wj.demo.framework.common.constant.BaseConstant;
import com.wj.demo.framework.common.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        if (StringUtils.isNotEmpty(langParam)) {
            locale = Locale.of(langParam.split("_")[0], langParam.split("_")[1]);
        }

        //语言
        TimeZone timeZone = TimeZone.getDefault();
        if (StringUtils.isNotEmpty(timeZoneParam)) {
            timeZone = TimeZone.getTimeZone(timeZoneParam);
        }

        //补充上下文信息
        BaseContext baseContext = BaseContextHolder.getBaseContext();
        baseContext.setLocale(locale)
                .setTimeZone(timeZone);

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
