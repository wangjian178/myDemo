package com.wj.demo.test.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.cors.*;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @ClassName CorsFilter
 * @Description: CORS拦截器
 * @Author: W.Jian
 * @CreateDate: 2025/4/15 13:46
 * @Version:
 */
public class CorsFilter extends OncePerRequestFilter {

    private final CorsConfigurationSource configSource;

    private CorsProcessor processor = new DefaultCorsProcessor();

    public CorsFilter(CorsConfigurationSource configSource) {
        Assert.notNull(configSource, "CorsConfigurationSource must not be null");
        this.configSource = configSource;
    }

    public void setCorsProcessor(CorsProcessor processor) {
        Assert.notNull(processor, "CorsProcessor must not be null");
        this.processor = processor;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CorsConfiguration corsConfiguration = this.configSource.getCorsConfiguration(request);
        boolean isValid = this.processor.processRequest(corsConfiguration, request, response);
        if (isValid && !CorsUtils.isPreFlightRequest(request)) {
            filterChain.doFilter(request, response);
        }
    }
}
