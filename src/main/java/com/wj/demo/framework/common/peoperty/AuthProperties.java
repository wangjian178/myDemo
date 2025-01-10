package com.wj.demo.framework.common.peoperty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 * @Desc
 */
@Component
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    private List<String> exclude;

    public List<String> getExclude() {
        return exclude;
    }

    public void setExclude(List<String> exclude) {
        this.exclude = exclude;
    }
}