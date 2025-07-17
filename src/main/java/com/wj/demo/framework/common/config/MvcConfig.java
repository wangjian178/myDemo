package com.wj.demo.framework.common.config;


import com.wj.demo.framework.common.property.SystemProperties;
import com.wj.demo.framework.interceptor.BaseContextInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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
    private SystemProperties systemProperties;

    /**
     * 按照顺序拦截
     *
     * @param registry 拦截器注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //权限访问
/*        registry.addInterceptor(new AuthInterceptor())
                .excludePathPatterns(systemProperties.getSecurity().getAuth().getExclude());*/

        //多语言 国际化
        registry.addInterceptor(new BaseContextInterceptor())
                .excludePathPatterns(systemProperties.getI18n().getExclude());
    }

    /**
     * 静态资源处理
     *
     * @param registry 资源处理器注册器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 明确指定静态资源路径
        registry.addResourceHandler("/static/**")
                .addResourceLocations(
                        "classpath:/static/",
                        "classpath:/public/",
                        "classpath:/resources/",
                        "classpath:/META-INF/resources/"
                );
    }
}
