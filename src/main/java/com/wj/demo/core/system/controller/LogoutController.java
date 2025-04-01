package com.wj.demo.core.system.controller;

import com.wj.demo.core.system.manager.LogoutManager;
import com.wj.demo.core.system.model.vo.LogoutResultVO;
import com.wj.demo.framework.exception.model.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName LogOutController
 * @Description: 登出
 * @Author: W.Jian
 * @CreateDate: 2025/4/1 14:10
 * @Version:
 */
@Tag(name = "登出功能")
@RestController
public class LogoutController {

    @Resource
    private LogoutManager logoutManager;

    @Operation(summary = "登出")
    @PostMapping("/logout")
    public Result<LogoutResultVO> logout(HttpServletRequest request) {
        return Result.ofSuccess(logoutManager.logout(request));
    }
}
