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
    LOGIN_CAPTCHA_EXPIRE_ERROR("601", "验证码超时！"),
    LOGIN_CAPTCHA_ERROR("602", "验证码错误！"),
    ;

    private final String code;

    private final String msg;

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
