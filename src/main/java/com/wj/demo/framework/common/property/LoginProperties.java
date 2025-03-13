package com.wj.demo.framework.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 * @Desc 登录接口
 */
@Component
@ConfigurationProperties(prefix = "login")
public class LoginProperties {

    private List<String> include;

    private List<String> exclude;

    public List<String> getInclude() {
        return include;
    }

    public void setInclude(List<String> include) {
        this.include = include;
    }

    public List<String> getExclude() {
        return exclude;
    }

    public void setExclude(List<String> exclude) {
        this.exclude = exclude;
    }
}