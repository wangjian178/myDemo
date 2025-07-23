package com.wj.demo.framework.common.utils;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/26 9:35
 */
public class StringUtils {

    /**
     * 判断字符串空
     * @param param 字符串
     * @return true:空
     */
    public static boolean isEmpty(String param) {
        return param == null || param.isEmpty();
    }

    /**
     * 判断字符串非空
     * @param param 字符串
     * @return true:非空
     */
    public static boolean isNotEmpty(String param) {
        return !isEmpty(param);
    }

    /**
     * 字符串截取
     * @param param 字符串
     * @param startIndex 开始索引
     * @param endIndex 结束索引
     * @return 截取后的字符串
     */
    public static String substring(String param, int startIndex, int endIndex) {
        if (isEmpty(param) || param.length() <= endIndex) {
            return param;
        }
        return param.substring(startIndex, endIndex);
    }
}
