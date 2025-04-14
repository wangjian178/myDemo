package com.wj.demo.framework.pageHelper.aop;


import com.github.pagehelper.PageHelper;
import com.wj.demo.framework.common.utils.ServletUtils;
import com.wj.demo.framework.pageHelper.PageContext;
import com.wj.demo.framework.pageHelper.constrant.PageConstants;
import com.wj.demo.framework.pageHelper.entity.Page;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName PageAspect
 * @Description: 分页切面
 * @Author: W.Jian
 * @CreateDate: 2025/4/14 11:23
 * @Version:
 */
@Aspect
@Component
public class PageAspect {

    /**
     * 切点是注解Pagination 环绕通知
     */
    @Pointcut("@annotation(com.wj.demo.framework.pageHelper.annotation.Pagination)")
    public void pagination() {
    }

    @Around("pagination()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取请求request
        if (!ServletUtils.isRequest()) {
            return joinPoint.proceed();
        }
        //获取分页参数
        Integer pageNum = ServletUtils.getParameterInt(PageConstants.PAGE_NUM);
        Integer pageSize = ServletUtils.getParameterInt(PageConstants.PAGE_SIZE);
        if (pageNum == null || pageSize == null) {
            return joinPoint.proceed();
        }
        String orderBy = ServletUtils.getParameter(PageConstants.ORDER_BY);
        com.github.pagehelper.Page<Object> pageResult = PageHelper.startPage(pageNum, pageSize, orderBy);
        Object result;
        try {
            result = joinPoint.proceed();

            //保存分页数据
            Page<?> page = new Page<>();
            page.setPageNum(pageResult.getPageNum());
            page.setPageSize(pageResult.getPageSize());
            page.setTotal(pageResult.getTotal());
            page.setPages(pageResult.getPages());
            page.setRecords((List) pageResult.getResult());
            PageContext.setPage(page);
        } finally {
            PageHelper.clearPage();
        }
        return result;
    }
}
