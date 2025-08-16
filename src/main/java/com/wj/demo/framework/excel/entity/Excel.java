package com.wj.demo.framework.excel.entity;

import lombok.Data;

import java.util.List;

/**
 * @ClassName CommonExcel
 * @Description: 公共excel类
 * @Author: W.Jian
 * @CreateDate: 2025/8/16 17:27
 * @Version:
 */
@Data
public class Excel {

    /**
     * excel名称
     */
    private String name;

    /**
     * sheet列表
     */
    private List<Sheet> sheetList;
}
