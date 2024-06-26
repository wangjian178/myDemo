package com.wj.demo.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @author wj
 * @version 1.0
 * @Desc 日期工具类
 * @date 2024/4/29 16:15
 */
public class DateUtils {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYY_MM_DD_HH_MM_DD = "yyyy-MM-dd HH:mm:ss";

    private static SimpleDateFormat getFormatter(String pattern) {
        return new SimpleDateFormat(Objects.requireNonNullElse(pattern, YYYY_MM_DD_HH_MM_DD));
    }

    /**
     * 当前日期
     */
    public static Date newDate() {
        return new Date();
    }

    /**
     * 转字符串
     */
    public static String parse(Date date) {
        return getFormatter(YYYY_MM_DD_HH_MM_DD).format(date);
    }

    /**
     * 日期转字符串
     */
    public static String parse(Date date, String pattern) {
        return getFormatter(pattern).format(date);
    }
}
