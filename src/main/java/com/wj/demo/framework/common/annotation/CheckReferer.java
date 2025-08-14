package com.wj.demo.framework.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName CheckReferer
 * @Description: referer防盗链
 * @Author: W.Jian
 * @CreateDate: 2025/8/14 10:09
 * @Version:
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckReferer {
    /**
     * 是否启用
     *
     * @return boolean
     */
    boolean enabled() default true;
}
