package com.wj.demo.framework.echarts.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 * @Desc echarts主体
 * @date 2024/5/24 15:28
 */
@Data
@Accessors(chain = true)
public class Echarts {

    /**
     * 标题
     */
    private Title title;
    /**
     * 表格类型
     */
    private Tooltip tooltip;
    /**
     * 图例
     */
    private Legend legend;
    /**
     * 网格偏移量
     */
    private Grid grid;
    /**
     *
     */
    private Toolbox toolbox;
    /**
     * x轴
     */
    private Axis xAxis;
    /**
     * y轴
     */
    private Axis yAxis;
    /**
     * 数据
     */
    private List<Series> series = new ArrayList<>();
}
