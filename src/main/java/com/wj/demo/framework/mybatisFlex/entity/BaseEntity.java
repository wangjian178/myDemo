package com.wj.demo.framework.mybatisFlex.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

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
    @Id(keyType = KeyType.Auto)
    @Schema(description = "ID")
    private Long id;

    /**
     * 创建人
     */
    @Column(value = "CREATE_BY")
    @Schema(description = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(value = "CREATE_TIME")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    @Column(value = "UPDATE_BY")
    @Schema(description = "修改人")
    private String updateBy;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(value = "UPDATE_TIME")
    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    @Column(value = "REMARK")
    @Schema(description = "备注")
    private String remark;

    /**
     * 逻辑删除 0否1是
     */
    @Column(value = "DELETED", isLogicDelete = true)
    @Schema(description = "删除标识0否1是")
    private Boolean deleted;
}
