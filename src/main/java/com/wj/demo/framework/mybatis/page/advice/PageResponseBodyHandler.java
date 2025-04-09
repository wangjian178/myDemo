package com.wj.demo.framework.mybatis.page.advice;

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

import java.util.List;

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

        //处理返回结果
        if (body instanceof Result result && Result.isSuccess(result) && result.getData() instanceof List data) {
            Result<Page> pageResult = Result.ofSuccess(
                    new Page<>()
                            .setPageNum(PageContext.getPage().getCurrent())
                            .setPageSize(PageContext.getPage().getSize())
                            .setTotal(PageContext.getPage().getTotal())
                            .setPages(PageContext.getPage().getPages())
                            .setRecords(data)
            );
            //清除分页信息
            PageContext.clear();
            return pageResult;
        } else if (body instanceof List data) {
            Result<Page> pageResult = Result.ofSuccess(
                    new Page<>()
                            .setPageNum(PageContext.getPage().getCurrent())
                            .setPageSize(PageContext.getPage().getSize())
                            .setTotal(PageContext.getPage().getTotal())
                            .setPages(PageContext.getPage().getPages())
                            .setRecords(data)
            );
            //清除分页信息
            PageContext.clear();
            return pageResult;
        }

        return body;
    }
}
