package com.wj.demo.baseContext;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/17 10:52
 */
public class BaseContextHolder {

    private static ThreadLocal<BaseContext> threadLocal = new ThreadLocal<>();

    /**
     * 设置上下文
     */
    public static void setContext(BaseContext baseContext) {
        threadLocal.set(baseContext);
    }

    /**
     * 获取上下文
     *
     * @return
     */
    public static BaseContext getBaseContext() {
        return threadLocal.get();
    }

    /**
     * 销毁删除
     */
    public static void remove() {
        threadLocal.remove();
    }
}
