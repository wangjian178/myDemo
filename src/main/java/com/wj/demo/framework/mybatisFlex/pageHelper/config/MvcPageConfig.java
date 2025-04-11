package com.wj.demo.framework.mybatisFlex.pageHelper.config;

import com.wj.demo.framework.mybatisFlex.pageHelper.interceptor.PageContextInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName MvcPageConfig
 * @Description: mvc配置-分页参数拦截
 * @Author: W.Jian
 * @CreateDate: 2025/4/7 17:15
 * @Version:
 */
@Configuration
public class MvcPageConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PageContextInterceptor());
    }
}
