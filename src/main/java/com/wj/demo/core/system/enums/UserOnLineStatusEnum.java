package com.wj.demo.core.system.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName UserStatusEnum
 * @Description: 用户在线状态
 * @Author: W.Jian
 * @CreateDate: 2025/4/1 15:39
 * @Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum UserOnLineStatusEnum {
    /**
     * 状态
     */
    ONLINE(1, "在线"),
    OFFLINE(0, "离线");

    @EnumValue
    private final Integer code;
    private final String desc;
}