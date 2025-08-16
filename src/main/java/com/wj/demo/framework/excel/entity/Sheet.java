package com.wj.demo.framework.excel.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @ClassName Sheet
 * @Description: Sheet页
 * @Author: W.Jian
 * @CreateDate: 2025/8/16 17:29
 * @Version:
 */
@Data
@Accessors(chain = true)
public class Sheet {

    /**
     * sheet页索引
     */
    private Integer sheetIndex;

    /**
     * sheet页名称
     */
    private String sheetName;

    /**
     * 表头
     */
    private List<TableHeader> header;

    /**
     * 表体
     */
    private List<TableRow> body;
}
