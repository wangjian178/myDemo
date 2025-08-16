package com.wj.demo.framework.common.utils;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wj
 */
@Slf4j
@Component
public class SpringContextUtils implements ApplicationContextAware {

    @Getter
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        if (SpringContextUtils.applicationContext == null) {
            SpringContextUtils.applicationContext = applicationContext;
        }
    }

    /**
     * 根据名称获取bean
     *
     * @param name 名称
     * @return bean
     */
    public static Object getBean(String name) {
        Object bean = null;
        try {
            bean = getApplicationContext().getBean(name);
        } catch (Exception e) {
            log.error("根据名称获取bean失败，name：{}", name);
        }
        return bean;
    }

    /**
     * 根据类型获取bean
     *
     * @param clazz 类型
     * @param <T>   泛型
     * @return bean
     */
    public static <T> T getBean(Class<T> clazz) {
        T bean = null;
        try {
            bean = getApplicationContext().getBean(clazz);
        } catch (Exception e) {
            log.error("根据类型获取bean失败，clazz：{}", clazz);
        }
        return bean;
    }

    /**
     * 根据名称获取bean
     *
     * @param name 名称
     * @return bean
     */
    public static <T> T getBean(Class<T> clazz, String name) {
        T bean = null;
        try {
            bean = getApplicationContext().getBean(name, clazz);
        } catch (Exception e) {
            log.warn("根据名称类型获取bean失败，clazz：{} name：{}", clazz, name);
        }
        return bean;
    }

    /**
     * 检查是否包含某个bean
     *
     * @param name bean名称
     * @return boolean
     */
    public static boolean containsBean(String name) {
        return getApplicationContext().containsBean(name);
    }

    /**
     * 根据类型获取beans
     *
     * @param clazz 类型
     * @param <T>   泛型
     * @return List<T>
     */
    public static <T> List<T> getBeans(Class<T> clazz) {
        Map<String, T> beanMap = getApplicationContext().getBeansOfType(clazz);
        return new ArrayList<>(beanMap.values());
    }
}