package com.wj.demo.common.utils;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wj
 * @version 1.0
 * @Desc 格式转换工具类
 * @date 2024/6/24 17:16
 */
public class ModelConvertUtils {

    /**
     * 对象转JSON
     * 只保留最外层
     *
     * @param t   对象
     * @param <T> 泛型
     * @return 返回值
     */
    public static <T> JSONObject convertModel(T t) {
        Class<?> clazz = t.getClass();
        List<Field> fields = FieldUtils.getAllFields(clazz);

        JSONObject result = new JSONObject();
        for (Field field : fields) {
            String name = field.getName();
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(t);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (value == null) {
                continue;
            }
            //日期做转换
            if (Date.class.equals(value.getClass()) || Timestamp.class.equals(value.getClass())) {
                value = DateUtils.format((Date) value);
            }
            //对象不保留 可以判断四类八种
            try {
                String jsonValue = JSONObject.toJSONString(value);
                JSONObject jsonObject = JSONObject.parseObject(jsonValue);
                if (jsonObject != null) {
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            result.put(name, value);
        }

        return result;
    }

    /**
     * 对象转JSON
     * 只保留最外层
     *
     * @param list 参数
     * @return 返回值
     */
    public static <T> List<JSONObject> convertList(List<T> list) {
        return list.stream().map(ModelConvertUtils::convertModel).collect(Collectors.toList());
    }
}
