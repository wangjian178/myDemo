package com.wj.demo.common.utils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wj
 * @version 1.0
 * @Desc 任务调度工具类
 * @date 2024/5/10 11:37
 */
public class ScheduledExecutorUtils {

    /**
     * 执行器（线程池）
     */
    private static final ScheduledExecutorService EXECUTOR = new ScheduledThreadPoolExecutor(3);

    /**
     * 任务列表
     */
    private static final Map<Long, ScheduledFuture<?>> TASK_POOL = new HashMap<>();


    /**
     * 守护线程 防止TASK_POOL一直新增 OOM
     */
    static {
        scheduleWithFixedDelay(() -> {
            List<Long> idList = TASK_POOL.keySet().stream().toList();
            for (Long id : idList) {
                ScheduledFuture<?> future = TASK_POOL.get(id);
                if (future.isCancelled() || future.isDone()) {
                    TASK_POOL.remove(id);
                }
            }
        }, 1, 1, TimeUnit.HOURS);
    }

    /**
     * 生成ID
     *
     * @return 返回结果
     */
    public static Long generateId() {
        return TASK_POOL.keySet().stream().max(Comparator.comparingLong(x -> x)).orElse(0L) + 1;
    }

    /**
     * 延迟 delay 后执行 command
     *
     * @param command 命令
     * @param delay   延迟时间
     * @param unit    单位
     * @return 返回结果
     */
    public static ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        ScheduledFuture<?> future = EXECUTOR.schedule(command, delay, unit);
        TASK_POOL.put(generateId(), future);
        return future;
    }

    /**
     * 延迟 initialDelay 后执行 command 每隔delay之后都执行 当前任务结束才开始计时
     *
     * @param command      命令
     * @param initialDelay 首次间隔时间
     * @param delay        间隔时间
     * @param unit         单位
     * @return 返回结果
     */
    public static ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        ScheduledFuture<?> future = EXECUTOR.scheduleWithFixedDelay(command, initialDelay, delay, unit);
        TASK_POOL.put(generateId(), future);
        return future;
    }

    /**
     * 延迟 initialDelay 后执行 command 每隔delay之后都执行 当前任务执行就开始计时
     *
     * @param command      命令
     * @param initialDelay 首次间隔时间
     * @param period       间隔时间
     * @param unit         单位
     */
    public static ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        ScheduledFuture<?> future = EXECUTOR.scheduleAtFixedRate(command, initialDelay, period, unit);
        TASK_POOL.put(generateId(), future);
        return future;
    }

    /**
     * 取消任务
     *
     * @param id 主键
     */
    public static void cancel(Long id) {
        ScheduledFuture<?> future = TASK_POOL.get(id);
        if (future == null) {
            return;
        }
        future.cancel(true);
        TASK_POOL.remove(id);
    }
}
