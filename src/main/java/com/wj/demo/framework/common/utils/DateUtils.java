package com.wj.demo.framework.common.utils;

import com.wj.demo.framework.exception.exception.BaseException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @author wj
 * @version 1.0
 * @Desc 日期工具类
 * @date 2024/4/29 16:15
 */
public class DateUtils {

    public static final String YYYY = "yyyy";

    public static final String MM = "MM";

    public static final String DD = "dd";

    public static final String YYYYMMDD = "yyyyMMdd";

    public static final String YYYY_MM_DD_SLASH = "yyyy/MM/dd";

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

    /**
     * 日期加毫秒
     *
     * @param date         日期
     * @param milliseconds 毫秒
     * @return 日期
     */
    public static Date addMilliseconds(Date date, int milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MILLISECOND, milliseconds);
        return calendar.getTime();
    }


    /**
     * 日期加秒
     *
     * @param date    日期
     * @param seconds 秒
     * @return 日期
     */
    public static Date addSeconds(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * 日期加分钟
     *
     * @param date    日期
     * @param minutes 分钟
     * @return 日期
     */
    public static Date addMinutes(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    /**
     * 日期加小时
     *
     * @param date  日期
     * @param hours 小时
     * @return 日期
     */
    public static Date addHours(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }

    /**
     * 日期加天数
     *
     * @param date 日期
     * @param days 天数
     * @return 日期
     */
    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 日期加月数
     *
     * @param date   日期
     * @param months 月数
     *               负数表示减月数
     */
    public static Date addMonths(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }
}
