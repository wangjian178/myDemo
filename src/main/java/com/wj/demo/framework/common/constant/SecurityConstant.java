package com.wj.demo.framework.common.constant;

/**
 * @ClassName SecurityConstant
 * @Description: 鉴权常量
 * @Author: W.Jian
 * @CreateDate: 2025/4/25 16:54
 * @Version:
 */
public class SecurityConstant {

    /**
     * token
     */
    public static final String AUTHORIZATION = "Authorization";

    /**
     * 鉴权Authorization前缀
     */
    public static final String AUTHORIZATION_PREFIX = "Bearer ";

    /**
     * 空字符串
     */
    public static final String EMPTY_STRING = "";

    /**
     * 鉴权负载信息 用户id
     */
    public static final String USER_ID = "id";

    /**
     * 鉴权负载信息 用户名
     */
    public static final String USER_NAME = "username";
}
