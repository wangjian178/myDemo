package com.wj.demo.framework.startup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @ClassName StartUpRunner
 * @Description: 启动任务（初始化）
 * @Author: W.Jian
 * @CreateDate: 2025/3/31 10:58
 * @Version:
 */
@Slf4j
@Component
public class StartUpRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("项目已启动......................");
    }
}
