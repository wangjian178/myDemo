package com.wj.demo.echarts.enums;

/**
 * @author wj
 */
public enum TooltipEnum {

    /**
     * axis
     */
    AXIS("axis");

    private String code;

    TooltipEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}