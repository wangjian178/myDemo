package com.wj.demo.common.utils;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wj
 * @version 1.0
 * @Desc 任务调度工具类
 * @date 2024/5/10 11:37
 */
public class ScheduledExecutorUtils {

    private static final ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(3);

    public static void main(String[] args) {
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("10s后1");
            }
        }, 10, TimeUnit.SECONDS);
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("10s后2");
            }
        }, 10, TimeUnit.SECONDS);
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("10s后3");
            }
        }, 10, TimeUnit.SECONDS);
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("10s后4");
            }
        }, 10, TimeUnit.SECONDS);
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("20s后");
                executor.shutdown();
            }
        }, 20, TimeUnit.SECONDS);
    }
}
