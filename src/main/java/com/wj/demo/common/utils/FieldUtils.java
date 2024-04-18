package com.wj.demo.common.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/18 15:25
 */
public class FieldUtils {

    /**
     * 获取当前类所有的属性 包括父类
     *
     * @param clazz
     * @return
     */
    public static List<Field> getAllFields(Class clazz) {
        //当前类
        List<Field> result = getFields(clazz);
        //父类
        Class superclass = clazz.getSuperclass();
        while (superclass != null) {
            result.addAll(getFields(superclass));
            superclass = superclass.getSuperclass();
        }

        return result;
    }

    /**
     * 获取当前类 特有的属性
     *
     * @param clazz
     * @return
     */
    public static List<Field> getFields(Class clazz) {
        return Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList());
    }

    /**
     * 注解过滤类型
     *
     * @param fieldList
     * @param clazz
     * @return
     */
    public static List<Field> filterFieldsByAnnotation(List<Field> fieldList, Class<? extends Annotation> clazz) {
        return fieldList.stream().filter(x -> x.getAnnotation(clazz) != null).collect(Collectors.toList());
    }

    /**
     * 获取属性值
     *
     * @param field
     * @param object
     * @return
     */
    public static Object getFieldValue(Field field, Object object) {
        field.setAccessible(true);
        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
