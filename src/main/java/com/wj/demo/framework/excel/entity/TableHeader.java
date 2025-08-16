package com.wj.demo.framework.excel.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName ExcelHeader
 * @Description: 表头
 * @Author: W.Jian
 * @CreateDate: 2025/8/16 17:28
 * @Version:
 */
@Data
@Accessors(chain = true)
public class TableHeader {

    /**
     * 标题
     */
    private String title;
}
