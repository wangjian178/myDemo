package com.wj.demo.core.system.manager;

import com.wj.demo.core.system.model.vo.LoginParamVO;
import com.wj.demo.core.system.model.vo.LoginResultVO;
import com.wj.demo.core.system.model.vo.UserInfoVO;
import com.wj.demo.core.system.service.ILoginService;
import com.wj.demo.framework.baseContext.HandlerAdapter;
import com.wj.demo.framework.common.property.SystemProperties;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @ClassName ILoginManager
 * @Description: 登录管理器
 * @Author: W.Jian
 * @CreateDate: 2025/3/27 14:01
 * @Version:
 */
@Service
public class LoginManager {

    @Resource
    private SystemProperties systemProperties;

    /**
     * 登录
     *
     * @param loginParamVO 登录参数
     * @return 登录结果
     */
    public LoginResultVO login(LoginParamVO loginParamVO) {
        return HandlerAdapter.finderHandler(ILoginService.class, systemProperties.getSecurity().getLoginHandler()).login(loginParamVO);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    public UserInfoVO getUserInfo() {
        return HandlerAdapter.finderHandler(ILoginService.class, systemProperties.getSecurity().getLoginHandler()).getUserInfo();
    }
}
