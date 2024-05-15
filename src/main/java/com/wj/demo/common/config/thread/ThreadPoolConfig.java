package com.wj.demo.common.config.thread;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author wj
 * @version 1.0
 * @Desc 初始化线程池全局通用
 * @date 2024/5/15 8:57
 */
@Configuration
public class ThreadPoolConfig {

    @Resource
    private ThreadPoolProperties threadPoolProperties;

    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(threadPoolProperties.getCorePoolSize());
        // 最大线程数
        executor.setMaxPoolSize(threadPoolProperties.getMaxPoolSize());
        // 队列容量
        executor.setQueueCapacity(threadPoolProperties.getQueueCapacity());
        // 线程空闲时的存活时间
        executor.setKeepAliveSeconds(threadPoolProperties.getKeepAliveSeconds());
        // 线程名称的前缀
        executor.setThreadNamePrefix(threadPoolProperties.getThreadNamePrefix());
        // 拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }
}
