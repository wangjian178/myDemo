package com.wj.demo.core.system.service;


import com.wj.demo.core.system.entity.SysScheduleJob;
import com.mybatisflex.core.service.IService;

/**
 * 系统-定时任务表 服务层。
 *
 * @author wj
 * @since 1.0.0
 */
public interface ISysScheduleJobService extends IService<SysScheduleJob> {


    /**
     * 添加系统-定时任务表
     * @param sysScheduleJob 系统-定时任务表
     */
    void add(SysScheduleJob sysScheduleJob);

    /**
     * 执行系统-定时任务表
     * @param sysScheduleJob 系统-定时任务表
     */
    void execute(SysScheduleJob sysScheduleJob);
}