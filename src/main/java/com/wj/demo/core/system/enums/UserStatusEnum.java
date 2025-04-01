package com.wj.demo.core.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author W.Jian
 */

@Getter
@AllArgsConstructor
public enum UserStatusEnum {
    /**
     * 状态
     */
    ONLINE(1, "在线"),
    OFFLINE(0, "离线");

    private final Integer code;
    private final String desc;
}