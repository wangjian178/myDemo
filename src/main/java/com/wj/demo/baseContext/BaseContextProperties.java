package com.wj.demo.baseContext;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wj
 */
@Component
@ConfigurationProperties(prefix = "basecontext")
public class BaseContextProperties {

    private List<String> exclude;


    public List<String> getExclude() {
        return exclude;
    }

    public void setExclude(List<String> exclude) {
        this.exclude = exclude;
    }
}