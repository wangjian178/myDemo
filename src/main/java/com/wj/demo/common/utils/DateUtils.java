package com.wj.demo.common.utils;

import com.wj.demo.exception.exception.BaseException;

import java.text.ParseException;
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

    public static final String YYYYMMDD = "yyyyMMdd";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYY_MM_DD_HH_MM_DD = "yyyy-MM-dd HH:mm:ss";

    private static SimpleDateFormat getFormatter(String pattern) {
        return new SimpleDateFormat(Objects.requireNonNullElse(pattern, YYYY_MM_DD_HH_MM_DD));
    }

    /**
     * 当前日期
     */
    public static Date today() {
        String dateString = format(now(), YYYY_MM_DD);
        return parse(dateString, YYYY_MM_DD);
    }

    /**
     * 当前时间
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 日期转字符串
     */
    public static String format(Date date) {
        return getFormatter(YYYY_MM_DD_HH_MM_DD).format(date);
    }

    /**
     * 日期转字符串
     */
    public static String format(Date date, String pattern) {
        return getFormatter(pattern).format(date);
    }

    /**
     * 字符串转时间
     */
    public static Date parse(String dateString) {
        SimpleDateFormat formatter = getFormatter(YYYY_MM_DD_HH_MM_DD);
        Date date = null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            throw new BaseException(e.getMessage());
        }
        return date;
    }

    /**
     * 字符串转日期
     */
    public static Date parse(String dateString, String pattern) {
        SimpleDateFormat formatter = getFormatter(pattern);
        Date date = null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            throw new BaseException(e.getMessage());
        }
        return date;
    }
}
