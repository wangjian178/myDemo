package com.wj.demo.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wj
 */
@Slf4j
@Component
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringContextUtils.applicationContext == null) {
            SpringContextUtils.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据名称获取bean
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        Object bean = null;
        try {
            bean = getApplicationContext().getBean(name);
        } catch (Exception e) {

        }
        return bean;
    }

    /**
     * 根据类型获取bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        T bean = null;
        try {
            bean = getApplicationContext().getBean(clazz);
        } catch (Exception e) {

        }
        return bean;
    }

    /**
     * 根据名称获取bean
     *
     * @param name
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        T bean = null;
        try {
            bean = getApplicationContext().getBean(name, clazz);
        } catch (Exception e) {

        }
        return bean;
    }

    /**
     * 检查是否包含某个bean
     *
     * @param name
     * @return
     */
    public static boolean containsBean(String name) {
        return getApplicationContext().containsBean(name);
    }

    /**
     * 根据类型获取beans
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> getBeans(Class<T> clazz) {
        Map<String, T> beanMap = getApplicationContext().getBeansOfType(clazz);
        return beanMap.values().stream().collect(Collectors.toList());
    }
}