package com.wj.demo.framework.common.property;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 * @Desc
 */
@Getter
@Setter
public class AuthProperties {

    /**
     * 白名单
     */
    private List<String> exclude;

}