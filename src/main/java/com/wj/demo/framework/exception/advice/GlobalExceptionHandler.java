package com.wj.demo.framework.exception.advice;

import com.wj.demo.framework.exception.exception.BaseException;
import com.wj.demo.framework.exception.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author wj
 * @version 1.0
 * @Desc 全局异常处理
 * @date 2024/4/18 10:25
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 404异常
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<String> handleNotFound(NoHandlerFoundException e) {
        log.error("NoHandlerFoundException ", e);
        return Result.ofFail(String.valueOf(HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    /**
     * 自定义异常
     */
    @ExceptionHandler(BaseException.class)
    public Result<String> handleBaseException(BaseException e) {
        log.error("BaseException ", e);
        return Result.ofFail(e.getMessage());
    }

    /**
     * 默认异常
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        log.error("Exception ", e);
        return Result.ofFail(e.getMessage());
    }
}
