package com.wj.demo.framework.baseContext;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/17 10:52
 */
public class BaseContextHolder {

    private static final ThreadLocal<BaseContext> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 获取上下文
     * 不存在则默认创建一个空的
     */
    public static BaseContext getBaseContext() {
        BaseContext baseContext = CONTEXT_HOLDER.get();
        if (baseContext == null) {
            baseContext = BaseContext.build();
            CONTEXT_HOLDER.set(baseContext);
        }
        return baseContext;
    }

    /**
     * 销毁删除
     */
    public static void remove() {
        CONTEXT_HOLDER.remove();
    }
}
