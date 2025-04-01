package com.wj.demo.core.system.service;

import com.wj.demo.core.system.model.vo.LogoutResultVO;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @ClassName ILogoutService
 * @Description: 登出服务
 * @Author: W.Jian
 * @CreateDate: 2025/4/1 14:13
 * @Version:
 */
public interface ILogoutService {

    /**
     * 登出
     *
     * @return token
     */
    LogoutResultVO logout(HttpServletRequest request);
}
