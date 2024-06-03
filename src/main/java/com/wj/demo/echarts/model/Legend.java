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
public class Legend {
    private List<String> data = new ArrayList<>();;
}