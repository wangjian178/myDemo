package com.wj.demo.framework.mybatis.page.interceptor;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wj.demo.framework.common.utils.StringUtils;
import com.wj.demo.framework.mybatis.page.PageContext;
import com.wj.demo.framework.mybatis.page.annotation.Pagination;
import com.wj.demo.framework.mybatis.page.constrant.PageConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

/**
 * @ClassName PageContextInterceptor
 * @Description: 请求分页上下文拦截器
 * @Author: W.Jian
 * @CreateDate: 2025/4/7 17:06
 * @Version:
 */
public class PageContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断请求方法是否分页注解
        if (handler instanceof HandlerMethod handlerMethod) {
            if (handlerMethod.hasMethodAnnotation(Pagination.class)) {
                //请求分页参数
                String pageNum = request.getParameter(PageConstants.PAGE_NUM);
                String pageSize = request.getParameter(PageConstants.PAGE_SIZE);
                //排序条件
                String orderBy = request.getParameter(PageConstants.ORDER_BY);

                if (StringUtils.isNotEmpty(pageNum) && StringUtils.isNotEmpty(pageSize)) {
                    Page<?> page = new Page<>();
                    page.setCurrent(Long.parseLong(pageNum));
                    page.setSize(Long.parseLong(pageSize));
                    PageContext.setPage(page);
                    if (StringUtils.isNotEmpty(orderBy)) {
                        page.addOrder(
                                Arrays.stream(orderBy.split(PageConstants.COMMA))
                                        .map(item -> {
                                            OrderItem orderItem = new OrderItem();
                                            orderItem.setColumn(item.split(PageConstants.SPACE)[0]);
                                            orderItem.setAsc(PageConstants.ASC.equalsIgnoreCase(item.split(PageConstants.SPACE)[1]));
                                            return orderItem;
                                        }).toList()
                        );
                    }
                }
            }
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //请求结束 清除分页上下文
        PageContext.clear();
    }
}
