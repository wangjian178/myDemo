package com.wj.demo.framework.common.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName AllowedReferers
 * @Description: 允许的referer
 * @Author: W.Jian
 * @CreateDate: 2025/8/14 10:25
 * @Version: 1.0
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "system.request.allowed-referer")
public class AllowedReferer {

    /**
     * 允许的referer
     */
    private String[] referer;

}
