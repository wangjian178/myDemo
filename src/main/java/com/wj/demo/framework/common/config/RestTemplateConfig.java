package com.wj.demo.framework.common.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author wj
 * @version 1.0
 * @Desc rest请求模板
 * @date 2024/5/16 9:12
 */
@Configuration
public class RestTemplateConfig {

    /**
     * rest api 请求模板
     * 通过注入restTemplate 实现请求发送
     *
     * @return RestTemplate
     */
    @Bean(name = "restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .build();
    }
}
