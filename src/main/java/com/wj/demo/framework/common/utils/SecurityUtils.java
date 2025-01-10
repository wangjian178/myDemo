package com.wj.demo.framework.common.utils;

import com.wj.demo.framework.common.model.User;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/25 17:44
 */
public class SecurityUtils {

    /**
     * 获取用户信息 todo
     * @return
     */
    public static User getUser(){
        return new User().setId(1L);
    }
}
