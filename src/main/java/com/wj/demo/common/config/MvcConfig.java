package com.wj.demo.common.config;


import com.wj.demo.common.peoperty.LoginProperties;
import com.wj.demo.common.peoperty.BaseContextProperties;
import com.wj.demo.common.peoperty.AuthProperties;
import com.wj.demo.interceptor.BaseContextInterceptor;
import com.wj.demo.interceptor.AuthInterceptor;
import com.wj.demo.interceptor.LoginInterceptor;
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

    @Resource
    private AuthProperties authProperties;

    @Resource
    private LoginProperties loginProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //权限访问
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns(loginProperties.getInclude());

        //多语言 国际化
        registry.addInterceptor(new BaseContextInterceptor())
                .excludePathPatterns(baseContextProperties.getExclude());

        //权限访问
        registry.addInterceptor(new AuthInterceptor())
                .excludePathPatterns(authProperties.getExclude());
    }
}
