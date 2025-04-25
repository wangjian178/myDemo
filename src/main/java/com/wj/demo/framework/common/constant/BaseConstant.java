package com.wj.demo.framework.common.constant;

/**
 * @author wj
 * @version 1.0
 * @Desc 基本常量类
 * @date 2024/4/18 15:32
 */
public class BaseConstant {

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
     * 默认值
     */
    public static final String DEFAULT = "default";

    /**
     * 重试次数
     */
    public static final Integer LOCK_USER_MAX_RETRY_TIMES = 5;
    /**
     * 锁定时间s
     */
    public static final Long LOCK_USER_LOCK_SECONDS = 600L;
}
