package com.wj.demo.core.system.entity;


import com.mybatisflex.annotation.Table;
import com.wj.demo.framework.mybatisFlex.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 流水号生成
 *
 * @author W.Jian
 * @TableName SYS_IDENTITY
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Table(value = "SYS_IDENTITY")
@Schema(title = "SysIdentity", description = "流水号生成")
public class SysIdentity extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 编码
     */
    @Schema(description = "编码")
    private String code;

    /**
     * 规则
     */
    @Schema(description = "规则")
    private String ruler;

    /**
     * 生成类型 0=每天生成,1=每月生成,2=每年生成,3=递增
     */
    @Schema(description = "生成类型 0=每天生成,1=每月生成,2=每年生成,3=递增")
    private Integer type;

    /**
     * 流水号长度
     */
    @Schema(description = "流水号长度")
    private Integer noLength;

    /**
     * 初始值
     */
    @Schema(description = "初始值")
    private Integer initValue;

    /**
     * 当前值
     */
    @Schema(description = "当前值")
    private Integer currentValue;

    /**
     * 步长
     */
    @Schema(description = "步长")
    private Integer step;
}