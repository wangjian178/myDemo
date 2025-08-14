package com.wj.demo.core.system.controller;

import com.wj.demo.core.system.manager.LoginManager;
import com.wj.demo.core.system.model.vo.LoginParamVO;
import com.wj.demo.core.system.annotation.OperateLog;
import com.wj.demo.framework.common.enums.OperateTypeEnum;
import com.wj.demo.framework.exception.model.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SysLoginController
 * @Description: 登录功能
 * @Author: W.Jian
 * @CreateDate: 2025/3/19 15:03
 * @Version:
 */
@Tag(name = "登录功能")
@RestController
public class LoginController {

    @Resource
    private LoginManager loginManager;

    @OperateLog(module = "系统", function = "登录", operateType = OperateTypeEnum.OTHER)
    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginParamVO loginParamVO) {
        return Result.ofSuccess(loginManager.login(loginParamVO));
    }
}
