package com.wj.demo.framework.pageHelper.annotation;

import java.lang.annotation.*;

/**
 * @ClassName Pagination
 * @Description:
 * <h4>分页注解</h4>
 * <p>使用条件：1.必须添加注解 2.包含分页参数pageNum、pageSize</p>
 * <br>
 * <p>排序：orderBy = "id desc,create_time asc"</p>
 * @Author: W.Jian
 * @CreateDate: 2025/4/2 15:23
 * @Version:
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Pagination {

}
