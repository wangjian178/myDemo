package com.wj.demo.framework.mybatis.page;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @ClassName PageContext
 * @Description: 分页上下文
 * @Author: W.Jian
 * @CreateDate: 2025/4/7 16:30
 * @Version:
 */
public class PageContext {
    private static final ThreadLocal<IPage<?>> PAGE_HOLDER = new ThreadLocal<>();

    public static void setPage(IPage<?> page) {
        PAGE_HOLDER.set(page);
    }

    public static IPage<?> getPage() {
        return PAGE_HOLDER.get();
    }

    public static void clear() {
        PAGE_HOLDER.remove();
    }
}
