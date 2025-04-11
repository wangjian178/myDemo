package com.wj.demo.framework.mybatisFlex.pageHelper;

import com.mybatisflex.core.paginate.Page;

/**
 * @ClassName PageContext
 * @Description: 分页上下文
 * @Author: W.Jian
 * @CreateDate: 2025/4/7 16:30
 * @Version:
 */
public class PageContext {
    private static final ThreadLocal<Page<?>> PAGE_HOLDER = new ThreadLocal<>();

    public static void setPage(Page<?> page) {
        PAGE_HOLDER.set(page);
    }

    public static Page<?> getPage() {
        return PAGE_HOLDER.get();
    }

    public static void clear() {
        PAGE_HOLDER.remove();
    }
}
