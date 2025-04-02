package com.wj.demo.core.system.controller;

import com.wj.demo.core.system.entity.SysUser;
import com.wj.demo.core.system.service.SysUserService;
import com.wj.demo.framework.exception.model.Result;
import com.wj.demo.framework.mybatis.vo.PageVO;
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
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

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
    public Result<Integer> edit(@RequestBody SysUser sysUser) {
        return Result.ofSuccess(sysUserService.edit(sysUser));
    }

    @Tag(name = "用户删除-批量")
    @DeleteMapping("/delete")
    public Result<Integer> delete(@RequestBody List<Long> idList) {
        return Result.ofSuccess(sysUserService.delete(idList));
    }

    @Tag(name = "用户列表-分页")
    @PostMapping("/list")
    public Result<PageVO<SysUser>> list(@RequestBody SysUser param) {
        sysUserService.list();
        return Result.ofSuccess();
    }
}
