package com.wj.demo.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/29 16:15
 */
public class DateUtils {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYY_MM_DD_HH_MM_DD = "yyyy-MM-dd HH:mm:ss";

    private static SimpleDateFormat getFormatter(String pattern) {
        if (pattern == null) {
            return new SimpleDateFormat(YYYY_MM_DD_HH_MM_DD);
        }
        return new SimpleDateFormat(pattern);
    }

    public static Date newDate() {
        return new Date();
    }

    public static String parse(Date date) {
        return getFormatter(YYYY_MM_DD_HH_MM_DD).format(date);
    }

    public static String parse(Date date, String pattern) {
        return getFormatter(pattern).format(date);
    }
}
