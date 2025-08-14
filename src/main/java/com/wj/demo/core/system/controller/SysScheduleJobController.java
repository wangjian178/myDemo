package com.wj.demo.core.system.controller;

import com.wj.demo.core.system.entity.SysScheduleJob;
import com.wj.demo.core.system.service.ISysScheduleJobService;
import com.wj.demo.core.system.annotation.OperateLog;
import com.wj.demo.framework.common.enums.OperateTypeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 系统-定时任务表 控制层。
 *
 * @author wj
 * @since 1.0.0
 */
@RestController
@RequestMapping("/system/sysScheduleJob")
@Tag(name = "系统-定时任务表控制层")
public class SysScheduleJobController {

    @Resource
    private ISysScheduleJobService sysScheduleJobService;

    /**
     * 添加 系统-定时任务表
     *
     * @param sysScheduleJob 系统-定时任务表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/save")
    @Operation(summary = "添加系统-定时任务表")
    public boolean save(@RequestBody SysScheduleJob sysScheduleJob) {
        return sysScheduleJobService.save(sysScheduleJob);
    }


    /**
     * 根据主键删除系统-定时任务表
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/remove/{id}")
    @Operation(summary = "根据主键删除系统-定时任务表")
    public boolean remove(@PathVariable Serializable id) {
        return sysScheduleJobService.removeById(id);
    }


    /**
     * 根据主键更新系统-定时任务表
     *
     * @param sysScheduleJob 系统-定时任务表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    @Operation(summary = "根据主键更新系统-定时任务表")
    public boolean update(@RequestBody SysScheduleJob sysScheduleJob) {
        return sysScheduleJobService.updateById(sysScheduleJob);
    }


    /**
     * 查询所有系统-定时任务表
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    @Operation(summary = "查询所有系统-定时任务表")
    public List<SysScheduleJob> list() {
        return sysScheduleJobService.list();
    }


    /**
     * 根据系统-定时任务表主键获取详细信息。
     *
     * @param id sysScheduleJob主键
     * @return 系统-定时任务表详情
     */
    @GetMapping("/getInfo/{id}")
    @Operation(summary = "根据系统-定时任务表主键获取详细信息")
    public SysScheduleJob getInfo(@PathVariable Serializable id) {
        return sysScheduleJobService.getById(id);
    }

    /**
     * 系统-定时任务-执行
     *
     * @param scheduleJob 任务
     */
    @PostMapping("/execute")
    @OperateLog(module = "系统-定时任务", function = "执行任务", operateType = OperateTypeEnum.OTHER)
    public void execute(@RequestBody SysScheduleJob scheduleJob) {
        sysScheduleJobService.execute(scheduleJob);
    }
}