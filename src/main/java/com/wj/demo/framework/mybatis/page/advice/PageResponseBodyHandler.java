package com.wj.demo.framework.mybatis.page.advice;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wj.demo.framework.exception.model.Result;
import com.wj.demo.framework.mybatis.page.PageContext;
import com.wj.demo.framework.mybatis.page.annotation.Pagination;
import com.wj.demo.framework.mybatis.page.entity.Page;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @ClassName PageResponseBodyHandler
 * @Description: 分页处理返回数据
 * @Author: W.Jian
 * @CreateDate: 2025/4/7 17:25
 * @Version:
 */
@Order(value = 998)
@RestControllerAdvice
public class PageResponseBodyHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        //带有分页注解Pagination才封装
        return returnType.hasMethodAnnotation(Pagination.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        IPage page = PageContext.getPage();
        return Result.ofSuccess(new Page<>()
                .setPageNum(page.getCurrent())
                .setPageSize(page.getSize())
                .setTotal(page.getTotal())
                .setPages(page.getPages())
                .setRecords(page.getRecords()));
    }
}
