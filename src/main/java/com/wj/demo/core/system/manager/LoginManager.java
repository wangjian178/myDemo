package com.wj.demo.core.system.manager;

import com.wj.demo.core.system.config.property.SysConfigProperty;
import com.wj.demo.core.system.model.vo.LoginParamVO;
import com.wj.demo.core.system.model.vo.LoginResultVO;
import com.wj.demo.core.system.service.ILoginService;
import com.wj.demo.framework.baseContext.HandlerAdapter;
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
    private SysConfigProperty sysConfigProperty;

    public LoginResultVO login(LoginParamVO loginParamVO) {
        return HandlerAdapter.finderHandler(ILoginService.class, sysConfigProperty.getLoginHandler()).login(loginParamVO);
    }
}
