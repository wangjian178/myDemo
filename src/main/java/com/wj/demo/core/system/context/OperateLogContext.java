package com.wj.demo.core.system.context;

import com.wj.demo.core.system.entity.SysOperateLog;

/**
 * @ClassName OperateLogContext
 * @Description: 操作日志线程变量
 * @Author: W.Jian
 * @CreateDate: 2025/7/23 16:06
 * @Version:
 */
public class OperateLogContext {

    private static final ThreadLocal<SysOperateLog> LOG = new ThreadLocal<>();

    public static void set(SysOperateLog log) {
        LOG.set(log);
    }

    public static SysOperateLog get() {
        return LOG.get();
    }

    public static void remove() {
        LOG.remove();
    }
}
