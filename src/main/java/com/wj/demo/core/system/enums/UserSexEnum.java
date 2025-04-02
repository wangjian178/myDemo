package com.wj.demo.core.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName UserSexEnum
 * @Description: 用户性别
 * @Author: W.Jian
 * @CreateDate: 2025/4/1 15:43
 * @Version: 1.0
 */
@AllArgsConstructor
@Getter
public enum UserSexEnum {
    MALE(0, "男"),
    FEMALE(1, "女");

    private final Integer code;
    private final String desc;

    /**
     * 根据code获取枚举
     * @param code 编码
     * @return 枚举
     */
    public static UserSexEnum getEnumByCode(Integer code) {
        for (UserSexEnum userSexEnum : UserSexEnum.values()) {
            if (userSexEnum.getCode().equals(code)) {
                return userSexEnum;
            }
        }
        return null;
    }
}
