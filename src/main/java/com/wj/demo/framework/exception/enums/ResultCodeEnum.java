package com.wj.demo.framework.exception.enums;

import lombok.Getter;

/**
 * @author wj
 * @version 1.0
 * @Desc 错误编码
 * @date 2024/4/18 11:04
 */
@Getter
public enum ResultCodeEnum {

    SYSTEM_ERROR("500", "未知错误"),
    CAPTCHA_CREATE_ERROR("601", "验证码生成失败！"),
    CAPTCHA_EXPIRE_ERROR("602", "验证码失效！"),
    CAPTCHA_ERROR("603", "验证码错误！"),
    FILE_NOT_FOUND_ERROR("703", "文件不存在！"),
    ;

    private final String code;

    private final String msg;

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
