package com.wj.demo.framework.excel;

import com.wj.demo.framework.excel.entity.Excel;

/**
 * @ClassName ExcelContextHolder
 * @Description: Excel读取过程中Excel数据
 * @Author: W.Jian
 * @CreateDate: 2025/8/16 19:47
 * @Version:
 */
public class ExcelContext {

    private static final ThreadLocal<Excel> CONTEXT = new ThreadLocal<>();

    /**
     * 初始化
     */
    public static void init() {
        CONTEXT.set(new Excel());
    }

    public static void set(Excel excel) {
        CONTEXT.set(excel);
    }

    public static Excel get() {
        return CONTEXT.get();
    }

    public static void remove() {
        CONTEXT.remove();
    }
}
