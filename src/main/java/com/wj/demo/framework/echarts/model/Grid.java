package com.wj.demo.framework.echarts.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wj
 */
@Data
@Accessors(chain = true)
public class Grid {
    private String left;
    private String right;
    private String bottom;
    private boolean containLabel;
}