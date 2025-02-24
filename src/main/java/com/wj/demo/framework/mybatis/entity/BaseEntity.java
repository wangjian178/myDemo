package com.wj.demo.framework.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.wj.demo.framework.mybatis.annotation.CreatedBy;
import com.wj.demo.framework.mybatis.annotation.CreatedTime;
import com.wj.demo.framework.mybatis.annotation.UpdatedBy;
import com.wj.demo.framework.mybatis.annotation.UpdatedTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author wj
 * @version 1.0
 * @Desc 基础实体类
 * @date 2024/4/17 15:20
 */
@Data
@Accessors(chain = true)
@Schema(title = "BaseEntity", description = "基础实体类")
public class BaseEntity {

    /**
     * ID
     * AUTO 自增
     * ASSIGN_ID支持自动生成
     * ASSIGN_UUID 字符串UUID
     */
    @TableId(type = IdType.AUTO, value = "ID")
    @Schema(description = "ID")
    private Long id;

    /**
     * 创建人
     */
    @CreatedBy
    @TableField(value = "CREATE_BY")
    @Schema(description = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @CreatedTime
    @TableField(value = "CREATE_TIME")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    @UpdatedBy
    @TableField(value = "UPDATE_BY")
    @Schema(description = "修改人")
    private String updateBy;

    /**
     * 修改时间
     */
    @UpdatedTime
    @TableField(value = "UPDATE_TIME")
    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    @TableField(value = "REMARK")
    @Schema(description = "备注")
    private String remark;

    /**
     * 逻辑删除 0否1是
     */
    @TableLogic
    @TableField(value = "DELETED")
    @Schema(description = "删除标识0否1是")
    private Boolean deleted;
}
