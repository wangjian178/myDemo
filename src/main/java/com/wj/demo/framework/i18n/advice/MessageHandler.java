package com.wj.demo.framework.i18n.advice;

import com.wj.demo.framework.baseContext.BaseContextHolder;
import com.wj.demo.framework.common.utils.StringUtils;
import com.wj.demo.framework.exception.model.Result;
import com.wj.demo.framework.i18n.CommonMessageSource;
import jakarta.annotation.Resource;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Locale;

/**
 * @author wj
 * @version 1.0
 * @Desc 国际化返回消息封装
 * @date 2024/4/29 16:48
 */
@Order(value = 1)
@RestControllerAdvice
public class MessageHandler implements ResponseBodyAdvice<Object> {

    @Resource
    private CommonMessageSource commonMessageSource;

    /**
     * 语言与服务器不一致时国际化
     *
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return !Locale.getDefault().equals(BaseContextHolder.getBaseContext().getLocale());
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body == null) {
            return body;
        } else if (body instanceof String) {
            return commonMessageSource.transferMsg(body.toString(), BaseContextHolder.getBaseContext().getLocale());
        } else if (body instanceof Result) {
            Result result = (Result) body;
            String msg = result.getMsg();
            if (StringUtils.isEmpty(msg)) {
                return body;
            }
            String message = commonMessageSource.transferMsg(msg, BaseContextHolder.getBaseContext().getLocale());
            result.setMsg(message);
            return result;
        }
        return body;
    }
}
