package com.wj.demo.core.system.enums;

import lombok.Getter;

/**
 * @ClassName DictStatus
 * @Description: 启用状态美剧
 * @Author: W.Jian
 * @CreateDate: 2025/7/26 17:31
 * @Version:
 */
@Getter
public enum EnableStatusEnum {

    ENABLE("ENABLE", "启用"),
    DISABLE("DISABLE", "停用");

    /**
     * 状态码
     */
    private final String code;

    /**
     * 状态描述
     */
    private final String label;

    EnableStatusEnum(String code, String label) {
        this.code = code;
        this.label = label;
    }
}
