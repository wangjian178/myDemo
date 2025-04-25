package com.wj.demo.core.system.service;

import com.wj.demo.framework.common.model.LoginUser;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @ClassName TokenService
 * @Description: token校验服务
 * @Author: W.Jian
 * @CreateDate: 2025/4/25 10:02
 * @Version:
 */
public interface TokenService {

    /**
     * 根据request获取token
     * @param request 请求
     * @return token
     */
    String getToken(HttpServletRequest request);

    /**
     * 根据request获取用户信息
     * @param request 请求
     * @return 用户信息
     */
    LoginUser getLoginUser(HttpServletRequest request);

    /**
     * 根据token获取用户信息
     * @param token token
     * @return 用户信息
     */
    LoginUser getLoginUser(String token);

    /**
     * 删除用户信息
     *
     * @param loginUser 用户信息
     */
    void removeLoginUser(LoginUser loginUser);
}
