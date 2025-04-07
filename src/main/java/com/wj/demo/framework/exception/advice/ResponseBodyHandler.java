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
@Order(value = 999)
@RestControllerAdvice
public class ResponseBodyHandler implements ResponseBodyAdvice<Object> {

    /**
     * 判断是否支持处理
     * @param returnType    返回类型
     * @param converterType 类型转换
     * @return 是否支持处理
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        //带有注解IgnoreAutoResponse不做封装
        return !returnType.hasMethodAnnotation(IgnoreAutoResponse.class);
    }

    /**
     * 处理返回值
     * @param body                  返回值
     * @param returnType            返回类型
     * @param selectedContentType   返回类型
     * @param selectedConverterType 返回类型
     * @param request               请求
     * @param response              响应
     * @return 处理后的返回值
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        return switch (body) {
            case null -> Result.ofSuccess();
            case Result<?> result -> body;
            case String s -> JSON.toJSONString(Result.ofSuccess(body.toString()));
            default -> Result.ofSuccess(body);
        };

    }

}
