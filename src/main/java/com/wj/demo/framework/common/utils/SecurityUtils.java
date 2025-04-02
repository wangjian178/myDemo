package com.wj.demo.framework.common.utils;

import com.wj.demo.framework.baseContext.BaseContextHolder;
import com.wj.demo.framework.common.constant.BaseConstant;
import com.wj.demo.framework.common.model.User;
import com.wj.demo.framework.redis.service.RedisClient;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/25 17:44
 */
public class SecurityUtils {

    /**
     * 获取用户信息 todo
     *
     * @return 用户
     */
    public static User getUser() {
        return SpringContextUtils
                .getBean(RedisClient.class)
                .get((BaseConstant.TOKEN_PREFIX + BaseContextHolder.getBaseContext().getToken()).replace(BaseConstant.AUTHORIZATION_PREFIX, BaseConstant.EMPTY_STRING));
    }

    /**
     * 获取用户信息
     *
     * @param token 鉴权信息
     * @return 当前用户
     */
    public static User getUser(String token) {
        return SpringContextUtils
                .getBean(RedisClient.class)
                .get((BaseConstant.TOKEN_PREFIX + token).replace(BaseConstant.AUTHORIZATION_PREFIX, BaseConstant.EMPTY_STRING));
    }
}
