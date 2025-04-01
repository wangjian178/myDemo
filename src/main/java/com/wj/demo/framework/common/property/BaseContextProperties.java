package com.wj.demo.framework.common.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 * @Desc 国际化接口
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "base-context")
public class BaseContextProperties {

    private List<String> exclude;
}