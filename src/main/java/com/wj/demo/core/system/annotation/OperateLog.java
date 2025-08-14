package com.wj.demo.core.system.annotation;

import com.wj.demo.framework.common.enums.OperateTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName Log
 * @Description: 操作日志
 * @Author: W.Jian
 * @CreateDate: 2025/7/18 11:48
 * @Version:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperateLog {
    /**
     * 模块
     */
    String module();

    /**
     * 功能
     */
    String function();

    /**
     * 操作类型
     */
    OperateTypeEnum operateType() default OperateTypeEnum.OTHER;

    /**
     * 是否记录参数 默认 true
     */
    boolean saveParams() default true;
}
