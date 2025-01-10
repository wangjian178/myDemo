package com.wj.demo.framework.exception.annotation;

import java.lang.annotation.*;

/**
 * @author wj
 * @version 1.0
 * @Desc 忽略请求响应体封装
 * @date 2024/4/29 16:35
 */

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreAutoResponse {
}
