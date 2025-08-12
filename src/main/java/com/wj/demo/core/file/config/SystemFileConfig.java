package com.wj.demo.core.file.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName UploadFileConfig
 * @Description: 文件上传配置
 * @Author: W.Jian
 * @CreateDate: 2025/7/27 17:56
 * @Version:
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "system.file")
public class SystemFileConfig {

    private String uploadPath;
}
