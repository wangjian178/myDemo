package com.wj.demo.test.security.config;

import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CorsFilter;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName CorsFilterConfig
 * @Description: 跨域过滤器配置
 * @Author: W.Jian
 * @CreateDate: 2025/4/15 14:21
 * @Version:
 */
@Configuration
public class CorsFilterConfig {

    /**
     * 配置Ajax跨域过滤器。
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        configSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(configSource);
    }

    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>(new CharacterEncodingFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("encoding", StandardCharsets.UTF_8.name());
        // forceEncoding强制response也被编码，另外即使request中已经设置encoding，forceEncoding也会重新设置
        filterRegistrationBean.addInitParameter("forceEncoding", "true");
        filterRegistrationBean.setAsyncSupported(true);
        return filterRegistrationBean;
    }
}
