package com.wj.demo.framework.echarts.model;

import com.wj.demo.framework.echarts.enums.TooltipEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wj
 */
@Data
@Accessors(chain = true)
public class Tooltip {

    private TooltipEnum trigger;
}

