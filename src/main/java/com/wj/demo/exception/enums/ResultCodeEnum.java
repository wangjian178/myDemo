package com.wj.demo.exception.enums;

/**
 * @author wj
 * @version 1.0
 * @Desc 错误编码
 * @date 2024/4/18 11:04
 */
public enum ResultCodeEnum {

    SYSTEM_ERROR("500", "未知错误");

    private String code;

    private String msg;

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
