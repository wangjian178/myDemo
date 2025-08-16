package com.wj.demo.framework.excel.entity;

import cn.idev.excel.metadata.data.ReadCellData;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @ClassName ExcelData
 * @Description: Excel数据
 * @Author: W.Jian
 * @CreateDate: 2025/8/16 17:28
 * @Version:
 */
@Data
@Accessors(chain = true)
public class TableRow {

    /**
     * 单元格数据 ReadCellData.class
     */
    private List<Object> cells;
}
