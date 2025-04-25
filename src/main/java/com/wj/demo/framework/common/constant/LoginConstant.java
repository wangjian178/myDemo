package com.wj.demo.framework.common.constant;

/**
 * @ClassName LoginConstant
 * @Description: 登录常量类
 * @Author: W.Jian
 * @CreateDate: 2025/4/25 17:01
 * @Version:
 */
public class LoginConstant {

    /**
     * 登录重试次数
     */
    public static final String LOGIN_LOCK_USER_RETRY_TIMES_KEY = "LOGIN:USER:COUNT:";
    /**
     * 登录验证码
     */
    public static final String LOGIN_CAPTCHA_KEY = "LOGIN:CAPTCHA:";

    /**
     * 登录Token前缀 + token
     */
    public static final String TOKEN_PREFIX = "LOGIN:USER:TOKEN:";
}
