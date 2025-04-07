package com.wj.demo.framework.mybatis.page.interceptor;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wj.demo.framework.common.utils.StringUtils;
import com.wj.demo.framework.mybatis.page.PageContext;
import com.wj.demo.framework.mybatis.page.constrant.PageConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

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
        //请求分页参数
        String pageNum = request.getParameter(PageConstants.PAGE_NUM);
        String pageSize = request.getParameter(PageConstants.PAGE_SIZE);

        if (StringUtils.isNotEmpty(pageNum) && StringUtils.isNotEmpty(pageSize)) {
            IPage<?> page = new Page<>();
            page.setCurrent(Long.parseLong(pageNum));
            page.setSize(Long.parseLong(pageSize));
            PageContext.setPage(page);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
