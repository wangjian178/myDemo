package com.wj.demo.framework.common.constant;

/**
 * @author wj
 * @version 1.0
 * @Desc 基本常量类
 * @date 2024/4/18 15:32
 */
public class BaseConstant {

    /**
     * 登陆页面
     */
    public static final String LOGIN_INDEX = "/sys/loginPage";

    /**
     * token
     */
    public static final String AUTHORIZATION = "Authorization";

    /**
     * 语言
     */
    public static final String LANGUAGE = "lang";
    /**
     * 时区
     */
    public static final String TIME_ZONE = "timeZone";

    /**
     * -
     */
    public static final String SLASH = "-";

    /**
     * 下划线
     */
    public static final String UNDERLINE = "_";

    /**
     * 空字符串
     */
    public static final String EMPTY_STRING = "";

    /**
     * 默认值
     */
    public static final String DEFAULT = "default";

    /**
     * 登录重试次数
     */
    public static final String LOGIN_LOCK_USER_RETRY_TIMES_KEY = "LOGIN:USER:COUNT:";
    /**
     * 登录验证码
     */
    public static final String LOGIN_CAPTCHA_KEY = "LOGIN:CAPTCHA:";
    /**
     * 重试次数
     */
    public static final Integer LOCK_USER_MAX_RETRY_TIMES = 5;
    /**
     * 锁定时间s
     */
    public static final Long LOCK_USER_LOCK_SECONDS = 600L;

    /**
     * 登录Token前缀 +userId
     */
    public static final String TOKEN_PREFIX = "LOGIN:USER:TOKEN:";

    /**
     * 鉴权Authorization前缀
     */
    public static final String AUTHORIZATION_PREFIX = "Bearer ";

    public static final String USER_ID = "id";

    public static final String USER_NAME = "username";
}
