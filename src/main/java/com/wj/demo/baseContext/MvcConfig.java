package com.wj.demo.baseContext;


import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/17 14:21
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Resource
    private BaseContextProperties baseContextProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BaseContextInterceptor())
                .excludePathPatterns(baseContextProperties.getExclude());
    }
}
