package com.wj.demo.core.system.controller;

import com.wj.demo.core.system.entity.SysOperateLog;
import com.wj.demo.core.system.service.ISysOperateLogService;
import com.wj.demo.framework.exception.model.Result;
import com.wj.demo.framework.pageHelper.annotation.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 系统操作日志 控制层。
 *
 * @author wj
 * @since 1.0.0
 */
@RestController
@RequestMapping("/sysOperateLog")
@Tag(name = "系统操作日志控制层")
public class SysOperateLogController {

    @Resource
    private ISysOperateLogService sysOperateLogService;

    /**
     * 查询所有系统操作日志
     *
     * @return 所有数据
     */
    @Pagination
    @GetMapping("/list")
    @Operation(summary = "查询所有系统操作日志")
    public Result<List<SysOperateLog>> list(SysOperateLog sysOperateLog) {
        return Result.ofSuccess(sysOperateLogService.list(sysOperateLog));
    }

    /**
     * 根据主键更新系统操作日志
     *
     * @param sysOperateLog 系统操作日志
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    @Operation(summary = "根据主键更新系统操作日志")
    public Result<Boolean> update(@RequestBody SysOperateLog sysOperateLog) {
        return Result.ofSuccess(sysOperateLogService.updateById(sysOperateLog));
    }


    /**
     * 根据系统操作日志主键获取详细信息。
     *
     * @param id sysOperateLog主键
     * @return 系统操作日志详情
     */
    @GetMapping("/getInfo/{id}")
    @Operation(summary = "根据系统操作日志主键获取详细信息")
    public Result<SysOperateLog> getInfo(@PathVariable Serializable id) {
        return Result.ofSuccess(sysOperateLogService.getById(id));
    }

    /**
     * 根据主键删除系统操作日志
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/remove/{id}")
    @Operation(summary = "根据主键删除系统操作日志")
    @Parameters(value = {
            @Parameter(name = "id", description = "ID", required = true)
    })
    public Result<Boolean> remove(@PathVariable Serializable id) {
        return Result.ofSuccess(sysOperateLogService.removeById(id));
    }
}