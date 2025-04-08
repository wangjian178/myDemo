package com.wj.demo.framework.mybatis.page.annotation;

import java.lang.annotation.*;

/**
 * @ClassName Pagination
 * @Description: 分页注解
 * 使用条件：1.必须添加注解 2.包含分页参数pageNum、pageSize
 * 排序：orderBy = "id desc,create_time asc"
 * @Author: W.Jian
 * @CreateDate: 2025/4/2 15:23
 * @Version:
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Pagination {

}
