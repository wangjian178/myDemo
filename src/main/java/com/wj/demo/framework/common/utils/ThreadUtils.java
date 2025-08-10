package com.wj.demo.framework.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @ClassName ThreadUtils
 * @Description: 线程工具类
 * @Author: W.Jian
 * @CreateDate: 2025/8/7 18:12
 * @Version:
 */
@Slf4j
public class ThreadUtils {

    /**
     * 定时任务线程池
     */
    private static ScheduledThreadPoolExecutor SCHEDULER;

    /**
     * 获取定时任务线程池
     *
     * @return 定时任务线程池
     */
    public static ScheduledThreadPoolExecutor getScheduler() {
        if (SCHEDULER == null) {
            SCHEDULER = new ScheduledThreadPoolExecutor(16, r -> {
                Thread thread = Executors.defaultThreadFactory().newThread(r);
                thread.setName("SCHEDULER-" + thread.threadId());
                thread.setDaemon(true);
                return thread;
            });
        }
        return SCHEDULER;
    }
}
