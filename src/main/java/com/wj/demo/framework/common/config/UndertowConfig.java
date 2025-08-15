package com.wj.demo.framework.common.config;

import io.undertow.server.DefaultByteBufferPool;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName UndertowConfig
 * @Description: Undertow的WebSocket缓冲池配置
 * @Author: W.Jian
 * @CreateDate: 2025/8/15 22:25
 * @Version:
 */
@Configuration
public class UndertowConfig {

    @Bean
    public WebServerFactoryCustomizer<UndertowServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.addDeploymentInfoCustomizers(deploymentInfo -> {
            WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();
            webSocketDeploymentInfo.setBuffers(new DefaultByteBufferPool(true, 1024));
            deploymentInfo.addServletContextAttribute(WebSocketDeploymentInfo.ATTRIBUTE_NAME, webSocketDeploymentInfo);
        });
    }
}
