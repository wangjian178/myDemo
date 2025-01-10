package com.wj.demo.framework.exception.advice;

import com.alibaba.fastjson.JSON;
import com.wj.demo.framework.exception.annotation.IgnoreAutoResponse;
import com.wj.demo.framework.exception.model.Result;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author wj
 * @version 1.0
 * @Desc Rest接口自动封装包装类 最后处理
 * @date 2024/4/29 16:32
 */
@Order(value = 99)
@RestControllerAdvice
public class ResponseBodyHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        //带有注解IgnoreAutoResponse不做封装
        return !returnType.hasMethodAnnotation(IgnoreAutoResponse.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (body == null) {
            return Result.ofSuccess();
        } else if (body instanceof Result<?>) {
            return body;
        } else if (body instanceof String) {
            return JSON.toJSONString(Result.ofSuccess(body.toString()));
        }

        return Result.ofSuccess(body);
    }

}
