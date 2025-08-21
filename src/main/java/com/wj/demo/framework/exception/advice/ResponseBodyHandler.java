package com.wj.demo.framework.exception.advice;

import com.alibaba.fastjson.JSON;
import com.wj.demo.framework.common.property.SystemProperties;
import com.wj.demo.framework.common.utils.ServletUtils;
import com.wj.demo.framework.exception.annotation.IgnoreAutoResponse;
import com.wj.demo.framework.exception.exception.BaseException;
import com.wj.demo.framework.exception.model.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 * @Desc Rest接口自动封装包装类 最后处理
 * @date 2024/4/29 16:32
 */
@Slf4j
@Order(value = 3)
@RestControllerAdvice
public class ResponseBodyHandler implements ResponseBodyAdvice<Object> {

    private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

    @Resource
    private SystemProperties systemProperties;

    /**
     * 判断是否支持处理
     *
     * @param returnType    返回类型
     * @param converterType 类型转换
     * @return 是否支持处理
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {

        //封装白名单
        List<String> ignoreUrls = systemProperties.getSecurity().getIgnoreResponseUrls();
        HttpServletRequest request = ServletUtils.getRequest();
        String relativePath = request.getRequestURI().substring(request.getContextPath().length());

        //带有注解IgnoreAutoResponse不做封装
        return ignoreUrls.stream().noneMatch(pattern -> PATH_MATCHER.match(pattern, relativePath)) && !returnType.hasMethodAnnotation(IgnoreAutoResponse.class);
    }

    /**
     * 处理返回值
     *
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
            case byte[] bytes -> bytes;
            case Result<?> result -> result;
            case String string -> {
                try {
                    yield JSON.toJSONString(Result.ofSuccess(string));
                } catch (Exception e) {
                    throw new BaseException(e);
                }
            }
            default -> Result.ofSuccess(body);
        };

    }

}
