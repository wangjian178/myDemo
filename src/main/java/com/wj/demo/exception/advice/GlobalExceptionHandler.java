package com.wj.demo.exception.advice;

import com.wj.demo.exception.exception.BaseException;
import com.wj.demo.exception.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author wj
 * @version 1.0
 * @Desc 全局异常处理
 * @date 2024/4/18 10:25
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public Result handleBaseException(BaseException e) {
        log.error("BaseException {}", e);
        return Result.ofFail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("Exception {}", e);
        return Result.ofFail(e.getMessage());
    }
}
