package com.wj.demo.echarts.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wj
 */
@Data
@Accessors(chain = true)
public class Series {

    /**
     * 对应图例legend
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     *堆栈
     */
    private String stack;

    /**
     *数据
     */
    private List<Integer> data = new ArrayList<>();;

}