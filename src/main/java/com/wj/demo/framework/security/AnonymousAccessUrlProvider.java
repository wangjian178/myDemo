package com.wj.demo.framework.security;

import com.wj.demo.framework.security.annotation.AnonymousAccess;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName AnonymousAccessUrlProvider
 * @Description: 白名单注解url提供类
 * @Author: W.Jian
 * @CreateDate: 2025/7/17 13:27
 * @Version:
 */
@Component
public class AnonymousAccessUrlProvider {

    private final List<String> urls = new ArrayList<>();

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo requestMappingInfo = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();
            AnonymousAccess anonymousAccess = handlerMethod.getMethodAnnotation(AnonymousAccess.class);
            if (anonymousAccess != null) {
                urls.addAll(requestMappingInfo.getPatternValues());
            }
        }
    }

    /**
     * 获取白名单url
     */
    public List<String> getIgnoreUrls() {
        return urls;
    }
}
