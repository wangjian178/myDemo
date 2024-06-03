package com.wj.demo.echarts.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * @author wj
 */
@Data
@Accessors(chain = true)
public class Axis {
    private String type;
    private boolean boundaryGap;
    private List<String> data;
}