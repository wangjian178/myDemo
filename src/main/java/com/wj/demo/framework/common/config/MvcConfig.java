package com.wj.demo.framework.common.config;


import com.wj.demo.framework.common.peoperty.LoginProperties;
import com.wj.demo.framework.common.peoperty.BaseContextProperties;
import com.wj.demo.framework.common.peoperty.AuthProperties;
import com.wj.demo.framework.interceptor.BaseContextInterceptor;
import com.wj.demo.framework.interceptor.AuthInterceptor;
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

    /**
     * 按照顺序拦截
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //权限访问
        registry.addInterceptor(new AuthInterceptor())
                .excludePathPatterns(authProperties.getExclude());

        //多语言 国际化
        registry.addInterceptor(new BaseContextInterceptor())
                .excludePathPatterns(baseContextProperties.getExclude());
    }
}
