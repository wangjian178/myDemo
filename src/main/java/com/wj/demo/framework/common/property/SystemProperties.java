package com.wj.demo.framework.common.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName SysConfigProperty
 * @Description: 系统配置属性
 * @Author: W.Jian
 * @CreateDate: 2025/3/19 16:35
 * @Version:
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "system")
public class SystemProperties {

    /**
     * 登录鉴权配置
     */
    private SecurityProperties security;

    /**
     * 国际化配置
     */
    private I18nProperties i18n;
}
