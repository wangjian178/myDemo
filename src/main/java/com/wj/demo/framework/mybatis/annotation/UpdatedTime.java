package com.wj.demo.framework.mybatis.annotation;

import java.lang.annotation.*;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/25 18:01
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UpdatedTime {
}
