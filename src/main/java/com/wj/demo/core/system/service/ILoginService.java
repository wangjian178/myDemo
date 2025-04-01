package com.wj.demo.core.system.service;

import com.wj.demo.core.system.model.vo.LoginParamVO;
import com.wj.demo.core.system.model.vo.LoginResultVO;

/**
 * @ClassName SysLoginService
 * @Description: 登录
 * @Author: W.Jian
 * @CreateDate: 2025/3/19 15:04
 * @Version:
 */
public interface ILoginService {

    /**
     * 登录
     * @param loginParamVO 登录参数
     * @return token
     */
    LoginResultVO login(LoginParamVO loginParamVO);
}
