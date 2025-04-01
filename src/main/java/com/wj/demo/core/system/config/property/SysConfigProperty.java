package com.wj.demo.core.system.config.property;

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
public class SysConfigProperty {

    /**
     * 登录处理器
     */
    private String loginHandler;

    /**
     * 登出处理器
     */
    private String logoutHandler;

    /**
     * 登陆页面地址
     */
    private String loginUrl;
}
