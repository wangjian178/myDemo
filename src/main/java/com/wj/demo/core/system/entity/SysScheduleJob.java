package com.wj.demo.core.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.wj.demo.framework.mybatisFlex.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 系统-定时任务表 实体类。
 *
 * @author wj
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data
@Schema(name = "系统-定时任务表")
@Table(value = "sys_schedule_job")
public class SysScheduleJob extends BaseEntity {

    /**
     * 任务名称
     */
    @Schema(description = "任务名称")
    @Column(value = "NAME")
    private String name;

    /**
     * 执行类
     */
    @Schema(description = "执行类")
    @Column(value = "JOB_CLASS")
    private String jobClass;

    /**
     * 执行方法
     */
    @Schema(description = "执行方法")
    @Column(value = "METHOD")
    private String method;

    /**
     * 参数
     */
    @Schema(description = "参数")
    @Column(value = "PARAMS")
    private String params;

    /**
     * 执行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "执行时间")
    @Column(value = "TRIGGER_TIME")
    private LocalDateTime triggerTime;

    /**
     * 执行状态 CREATED/RUNNING/COMPLETED
     */
    @Schema(description = "执行状态 CREATED/RUNNING/COMPLETED")
    @Column(value = "STATUS")
    private String status;

    /**
     * 构建定时任务
     *
     * @param name        任务名称
     * @param clazz       执行类
     * @param method      执行方法
     * @param params      执行参数
     * @param triggerTime 执行时间
     * @param <T>         执行类
     * @return 定时任务
     */
    public <T> SysScheduleJob build(String name, Class<T> clazz, String method, String params, LocalDateTime triggerTime) {
        return new SysScheduleJob()
                .setName(name)
                .setJobClass(clazz.getName())
                .setMethod(method)
                .setParams(params)
                .setTriggerTime(triggerTime);
    }
}
