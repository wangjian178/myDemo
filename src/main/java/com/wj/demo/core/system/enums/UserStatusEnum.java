package com.wj.demo.core.system.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName UserStatusEnum
 * @Description: 用户状态
 * @Author: W.Jian
 * @CreateDate: 2025/4/1 15:39
 * @Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnum {
    NORMAL(1, "正常"),
    LOCKED(2, "锁定"),
    DISABLE(3, "禁用"),
    ;

    @EnumValue
    private final Integer code;
    private final String desc;
}
