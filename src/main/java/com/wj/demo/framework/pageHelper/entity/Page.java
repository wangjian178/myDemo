package com.wj.demo.framework.pageHelper.entity;

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

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 每页数量
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer pages;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 结果集
     */
    private List<T> records;
}
