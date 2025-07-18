package com.wj.demo.framework.exception.exception;

import com.wj.demo.framework.exception.enums.ResultCodeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/18 10:56
 */
@Setter
@Getter
public class BaseException extends RuntimeException {

    private String code;

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
