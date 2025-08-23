package com.wj.demo.core.system.service;

import com.wj.demo.core.system.model.vo.LoginParamVO;
import com.wj.demo.core.system.model.vo.LoginResultVO;
import com.wj.demo.core.system.model.vo.UserInfoVO;
import com.wj.demo.framework.common.model.LoginUser;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @ClassName SysLoginService
 * @Description: 登录
 * @Author: W.Jian
 * @CreateDate: 2025/3/19 15:04
 * @Version:
 */
public interface ILoginService extends UserDetailsService {

    /**
     * 登录
     *
     * @param loginParamVO 登录参数
     * @return token
     */
    LoginResultVO login(LoginParamVO loginParamVO);

    /**
     * 生成token
     *
     * @param loginUser 用户
     * @return token
     */
    LoginResultVO createToken(LoginUser loginUser);

    /**
     * 获取用户信息
     * @return 用户信息
     */
    UserInfoVO getUserInfo();
}
