package com.wj.demo.core.system.controller;

import com.wj.demo.core.system.entity.SysUser;
import com.wj.demo.core.system.model.vo.SysUserPasswordVO;
import com.wj.demo.core.system.model.vo.SysUserVO;
import com.wj.demo.core.system.service.ISysUserService;
import com.wj.demo.framework.exception.model.Result;
import com.wj.demo.framework.pageHelper.annotation.Pagination;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName SysUserController
 * @Description: 用户管理
 * @Author: W.Jian
 * @CreateDate: 2025/4/2 14:17
 * @Version:
 */
@Tag(name = "用户管理")
@RestController
@RequestMapping("/system/user")
public class SysUserController {

    @Resource
    private ISysUserService sysUserService;

    @Tag(name = "注册")
    @PostMapping("/register")
    public Result<Integer> register(@RequestBody SysUser sysUser) {
        return Result.ofSuccess(sysUserService.register(sysUser));
    }

    @Tag(name = "批量同步")
    @PostMapping("/sync")
    public Result<Boolean> sync(@RequestBody List<SysUser> userList) {
        return Result.ofSuccess(sysUserService.sync(userList));
    }

    @Tag(name = "用户编辑")
    @PutMapping("/edit")
    public Result<Boolean> edit(@RequestBody SysUser sysUser) {
        return Result.ofSuccess(sysUserService.edit(sysUser));
    }

    @Tag(name = "用户删除-批量")
    @DeleteMapping("/delete")
    public Result<Integer> delete(@RequestBody List<Long> idList) {
        return Result.ofSuccess(sysUserService.delete(idList));
    }

    @Tag(name = "修改密码", description = "1.校验新老密码是否一致 \n 2.校验新密码强度 \n 3.修改")
    @PostMapping("/updatePassword")
    public Result<Boolean> updatePassword(@RequestBody SysUserPasswordVO sysUserPasswordVO) {
        return Result.ofSuccess(sysUserService.updatePassword(sysUserPasswordVO));
    }

    @Pagination
    @Tag(name = "用户列表-分页")
    @PostMapping("/list")
    public Result<List<SysUserVO>> list(@RequestBody SysUserVO sysUserVO) {
        return Result.ofSuccess(sysUserService.list(sysUserVO));
    }
}
