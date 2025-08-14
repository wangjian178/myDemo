package com.wj.demo.framework.exception.exception;

import com.wj.demo.framework.exception.enums.ResultCodeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName BusinessException
 * @Description: 业务异常 预期异常用这个
 * @Author: W.Jian
 * @CreateDate: 2025/8/14 14:30
 * @Version: 1.0
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {

    private String code;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMsg());
        this.code = resultCodeEnum.getCode();
    }
}
