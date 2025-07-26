package com.wj.demo.core.system.enums;

import lombok.Getter;

/**
 * @ClassName DictStyleEnum
 * @Description: 字典样式
 * @Author: W.Jian
 * @CreateDate: 2025/7/26 17:32
 * @Version:
 */
@Getter
public enum DictStyleEnum {

    STYLE_1("STYLE_1", "样式1"),
    STYLE_2("STYLE_2", "样式2"),
    STYLE_3("STYLE_3", "样式3"),
    STYLE_4("STYLE_4", "样式4"),
    ;

    /**
     * 编码
     */
    private final String code;
    /**
     * 描述
     */
    private final String label;

    DictStyleEnum(String code, String label) {
        this.code = code;
        this.label = label;
    }
}
