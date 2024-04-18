package com.wj.demo.common.annotation;

import java.lang.annotation.*;

/**
 * @author wj
 * @version 1.0
 * @Desc 字段顺序
 * @date 2024/4/18 15:37
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD})
@Documented
public @interface FieldOrder {
    int value() default Integer.MIN_VALUE;
}