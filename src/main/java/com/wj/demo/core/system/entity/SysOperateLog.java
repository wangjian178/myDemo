package com.wj.demo.core.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.wj.demo.framework.common.enums.OperateTypeEnum;
import com.wj.demo.framework.mybatisFlex.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @ClassName SysOperateLog
 * @Description: 操作日志
 * @Author: W.Jian
 * @CreateDate: 2025/7/18 11:25
 * @Version:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Table(value = "SYS_OPERATE_LOG")
@Schema(title = "SysOperateLog", description = "操作日志")
public class SysOperateLog extends BaseEntity {

    /**
     * 模块
     */
    @Schema(description = "模块")
    @Column(value = "MODULE")
    private String module;

    /**
     * 功能
     */
    @Schema(description = "功能")
    @Column(value = "FUNCTION")
    private String function;

    /**
     * 操作类型
     */
    @Schema(description = "操作类型")
    @Column(value = "OPERATE_TYPE")
    private OperateTypeEnum operateType;

    /**
     * 方法
     */
    @Schema(description = "方法")
    @Column(value = "METHOD")
    private String method;

    /**
     * 请求方式
     */
    @Schema(description = "请求方式")
    @Column(value = "REQUEST_METHOD")
    private String requestMethod;

    /**
     * IP
     */
    @Schema(description = "IP")
    @Column(value = "IP")
    private String ip;

    /**
     * ulr
     */
    @Schema(description = "url")
    @Column(value = "URL")
    private String url;

    /**
     * 参数
     */
    @Schema(description = "参数")
    @Column(value = "PARAMS")
    private String params;

    /**
     * 返回结果
     */
    @Schema(description = "返回结果")
    @Column(value = "RESULT")
    private String result;

    /**
     * 异常信息
     */
    @Schema(description = "异常信息")
    @Column(value = "EXCEPTION")
    private String exception;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "操作时间")
    @Column(value = "OPERATE_TIME")
    private LocalDateTime operateTime;
}
