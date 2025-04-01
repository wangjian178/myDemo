package com.wj.demo.core.system.manager;

import com.wj.demo.core.system.config.property.SysConfigProperty;
import com.wj.demo.core.system.model.vo.LogoutResultVO;
import com.wj.demo.core.system.service.ILogoutService;
import com.wj.demo.framework.baseContext.HandlerAdapter;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

/**
 * @ClassName LogoutManager
 * @Description: 登出管理器
 * @Author: W.Jian
 * @CreateDate: 2025/4/1 14:12
 * @Version:
 */
@Service
public class LogoutManager {

    @Resource
    private SysConfigProperty sysConfigProperty;

    public LogoutResultVO logout(HttpServletRequest request) {
        return HandlerAdapter.finderHandler(ILogoutService.class, sysConfigProperty.getLogoutHandler()).logout(request);
    }
}
