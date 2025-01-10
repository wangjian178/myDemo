package com.wj.demo.framework.common.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/23 15:25
 */
public class ServletUtils {

    /**
     * 获取Cookies
     *
     * @return
     */
    public static Cookie[] getCookies() {
        return getRequest().getCookies();
    }

    /**
     * 获取请求头
     *
     * @return
     */
    public static String getHeader(String header) {
        return getRequest().getHeader(header);
    }

    /**
     * 获取请求
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取请求属性
     *
     * @return
     */
    public static ServletRequestAttributes getRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    }
}
