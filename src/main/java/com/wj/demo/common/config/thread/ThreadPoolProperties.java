package com.wj.demo.common.config.thread;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wj
 */
@Component
@ConfigurationProperties(prefix = "thread")
@Data
public class ThreadPoolProperties {

    /**
     * 核心线程数
     */
    private int corePoolSize = 1;
    /**
     * 最大线程数
     */
    private int maxPoolSize = Integer.MAX_VALUE;
    /**
     * 最大存活时间
     */
    private int keepAliveSeconds = 60;
    /**
     * 最大堵塞队列数量
     */
    private int queueCapacity = Integer.MAX_VALUE;

    /**
     * 线程前缀
     */
    private String threadNamePrefix = "Task-";
}