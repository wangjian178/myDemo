package com.wj.demo.framework.springDoc.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName OpenApiConfig
 * @Description: springDoc配置
 * @Author: W.Jian
 * @CreateDate: 2025/8/15 18:16
 * @Version:
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "接口API",
                version = "1.0",
                description = "接口API文档"
        )
)
public class OpenApiConfig {

}
