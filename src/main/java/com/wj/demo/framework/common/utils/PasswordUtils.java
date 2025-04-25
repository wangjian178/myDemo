package com.wj.demo.framework.common.utils;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author wj
 * @version 1.0
 * @Desc 密码处理
 * @date 2024/7/11 16:46
 */
public class PasswordUtils {

    /**
     * 获取密码加密器
     */
    private static PasswordEncoder getPasswordEncoder() {
        return SpringContextUtils.getBean(PasswordEncoder.class);
    }


    /**
     * 加密
     *
     * @param password 密码
     */
    public static String encrypt(String password) {
        return getPasswordEncoder().encode(password);
    }

    /**
     * 解密 todo
     *
     * @param encryptPassword 加密密码
     */
    public static String decrypt(String encryptPassword) {
        return encryptPassword;
    }

    /**
     * 校验密码是否匹配
     *
     * @param password        密码
     * @param encryptPassword 加密密码
     */
    public static boolean match(String password, String encryptPassword) {
        return getPasswordEncoder().matches(password, encryptPassword);
    }
}
