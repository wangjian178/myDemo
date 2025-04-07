package com.wj.demo.framework.mybatis.enums;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/25 17:53
 */
public enum OperateType {

    INSERT("insert"),
    UPDATE("update"),
    DELETE("delete"),
    SELECT("select");


    private final String code;

    OperateType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
