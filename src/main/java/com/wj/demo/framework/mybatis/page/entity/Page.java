package com.wj.demo.framework.mybatis.page.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 * @Desc 分页结果
 * @date 2024/4/23 15:20
 */
@Data
@Accessors(chain = true)
public class Page<T>{

    private Long pageNum;

    private Long pageSize;

    private Long pages;

    private Long total;

    private List<T> records;
}
