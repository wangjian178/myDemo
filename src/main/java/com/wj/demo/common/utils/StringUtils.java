package com.wj.demo.common.utils;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/26 9:35
 */
public class StringUtils {

    public static boolean isEmpty(String param){
        return param==null || param.equals("");
    }

    public static boolean isNotEmpty(String param){
        return !isEmpty(param);
    }
}
