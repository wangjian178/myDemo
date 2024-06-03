package com.wj.demo.echarts.utils;

import com.wj.demo.echarts.model.Echarts;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 * @Desc echarts生成工具类
 * @date 2024/6/3 11:19
 */
public class EchartsUtils {

    /**
     * 条形图 todo 反射生成条形图
     */
    public static <T> Echarts bar(List<T> data, List<String> fieldNameList) {
        return new Echarts();
    }


    /**
     * 折线图 todo 反射生成折线图
     */
    public static <T> Echarts line(List<T> data, List<String> fieldNameList) {
        return new Echarts();
    }

    /**
     * 饼图 todo 反射生成饼图
     */
    public static <T> Echarts pie(List<T> data, List<String> fieldNameList) {

        return new Echarts();
    }
}
