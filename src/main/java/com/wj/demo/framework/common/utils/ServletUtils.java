package com.wj.demo.framework.common.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/23 15:25
 */
public class ServletUtils {

    /**
     * 获取Cookies
     */
    public static Cookie[] getCookies() {
        return getRequest().getCookies();
    }

    /**
     * 获取请求头
     */
    public static String getHeader(String header) {
        return getRequest().getHeader(header);
    }

    /**
     * 获取请求参数
     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * 获取请求参数
     */
    public static Integer getParameterInt(String name) {
        String parameter = getRequest().getParameter(name);
        return Objects.nonNull(parameter) ? Integer.parseInt(parameter) : null;
    }

    /**
     * 获取请求
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取请求属性
     */
    public static ServletRequestAttributes getRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    }
}
