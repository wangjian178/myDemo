package com.wj.demo.exception.annotation;

import java.lang.annotation.*;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/29 16:35
 */

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreAutoResponse {
}
