package com.wj.demo.core.system.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.wj.demo.core.system.enums.JobStatusEnum;
import com.wj.demo.framework.common.utils.StringUtils;
import com.wj.demo.framework.mybatisFlex.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 系统-定时任务表 实体类。
 *
 * @author wj
 * @since 1.0.0
 */
@Slf4j
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
    private JobStatusEnum status;

    /**
     * 错误信息
     */
    @Schema(description = "错误信息")
    @Column(value = "ERROR_MSG")
    private String errorMsg;

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
    public static <T> SysScheduleJob build(String name,
                                           Class<T> clazz,
                                           String method,
                                           Object[] params,
                                           LocalDateTime triggerTime) {
        return new SysScheduleJob()
                .setName(name)
                .setJobClass(clazz.getName())
                .setMethod(method)
                .setParams(JSONArray.toJSONStringWithDateFormat(params, JSONArray.DEFFAULT_DATE_FORMAT))
                .setTriggerTime(triggerTime);
    }


    /**
     * 将JSON参数转换为方法参数所需类型
     */
    public static Object[] convertParams(String jsonParams, Class<?>[] parameterTypes) {
        if (StringUtils.isEmpty(jsonParams)) {
            return new Object[0];
        }

        // 如果方法没有参数，忽略传入的参数
        if (parameterTypes.length == 0) {
            return new Object[0];
        }

        // 多参数情况（假设JSON是数组格式）
        try {
            JSONArray jsonArray = JSON.parseArray(jsonParams);
            Object[] params = new Object[parameterTypes.length];

            for (int i = 0; i < parameterTypes.length; i++) {
                if (i < jsonArray.size()) {
                    params[i] = JSON.parseObject(jsonArray.getString(i), parameterTypes[i]);
                } else {
                    params[i] = null;
                }
            }
            return params;
        } catch (Exception e) {
            log.error("SysScheduleJob任务执行失败 参数解析失败: {}", e.getMessage());
            Object[] params = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                params[i] = null;
            }
            return params;
        }
    }
}
