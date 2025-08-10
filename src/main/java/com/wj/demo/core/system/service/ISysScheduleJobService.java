package com.wj.demo.core.system.service;


import com.wj.demo.core.system.entity.SysScheduleJob;
import com.mybatisflex.core.service.IService;
import com.wj.demo.core.system.enums.JobStatusEnum;

import java.io.Serializable;

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
     * 延迟执行
     *
     * @param sysScheduleJob 系统-延迟定时任务
     */
    void delayExecute(SysScheduleJob sysScheduleJob);

    /**
     * 执行系统-定时任务表
     * @param sysScheduleJob 系统-定时任务表
     */
    void execute(SysScheduleJob sysScheduleJob);

    /**
     * 更新状态
     *
     * @param id     任务id
     * @param status 状态
     */
    void updateStatus(Serializable id, JobStatusEnum status);
}