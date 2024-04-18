package com.wj.demo.exception.exception;

import com.wj.demo.exception.enums.ResultCodeEnum;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/18 10:56
 */
public class BaseException extends RuntimeException {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BaseException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMsg());
        this.code = resultCodeEnum.getCode();
    }

}
