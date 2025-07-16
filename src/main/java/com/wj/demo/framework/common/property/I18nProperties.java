package com.wj.demo.framework.common.property;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 * @Desc 国际化接口
 */
@Getter
@Setter
public class I18nProperties {

    /**
     * 白名单
     */
    private List<String> exclude;
}