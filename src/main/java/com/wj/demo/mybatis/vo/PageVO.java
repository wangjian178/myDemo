package com.wj.demo.mybatis.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 * @Desc 分页
 * @date 2024/4/23 15:20
 */
@Data
@Accessors(chain = true)
public class PageVO<T> {

    private Integer pageNum;

    private Integer pageSize;

    private Long total;

    private List<T> data;
}
