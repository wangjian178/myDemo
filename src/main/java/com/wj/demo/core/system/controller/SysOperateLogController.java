package com.wj.demo.core.system.controller;

import com.wj.demo.core.system.entity.SysOperateLog;
import com.wj.demo.core.system.service.ISysOperateLogService;
import com.wj.demo.framework.pageHelper.annotation.Pagination;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/system/sysOperateLog")
@Tag(name = "系统操作日志控制层")
public class SysOperateLogController {

    @Resource
    private ISysOperateLogService sysOperateLogService;

    /**
     * 查询所有系统操作日志
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    @Operation(summary = "查询所有系统操作日志")
    @Pagination
    public List<SysOperateLog> list(SysOperateLog sysOperateLog) {
        return sysOperateLogService.list(sysOperateLog);
    }

    /**
     * 根据主键更新系统操作日志
     *
     * @param sysOperateLog 系统操作日志
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    @Operation(summary = "根据主键更新系统操作日志")
    public Boolean update(@RequestBody SysOperateLog sysOperateLog) {
        return sysOperateLogService.updateById(sysOperateLog);
    }


    /**
     * 根据系统操作日志主键获取详细信息。
     *
     * @param id sysOperateLog主键
     * @return 系统操作日志详情
     */
    @GetMapping("/getInfo/{id}")
    @Operation(summary = "根据系统操作日志主键获取详细信息")
    public SysOperateLog getInfo(@PathVariable Serializable id) {
        return sysOperateLogService.getById(id);
    }

    /**
     * 根据主键删除系统操作日志
     *
     * @param ids 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @PostMapping("/remove")
    @Operation(summary = "根据主键删除系统操作日志")
    public boolean remove(@RequestBody List<Serializable> ids) {
        return sysOperateLogService.removeByIds(ids);
    }
}