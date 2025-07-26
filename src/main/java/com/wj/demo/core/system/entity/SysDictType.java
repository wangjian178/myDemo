package com.wj.demo.core.system.entity;

import com.wj.demo.core.system.enums.EnableStatusEnum;
import com.wj.demo.framework.mybatisFlex.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

import java.util.Date;
import java.lang.String;
import java.lang.Integer;

/**
 * 系统-字典类型 实体类。
 *
 * @author wj
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data
@Schema(name = "系统-字典类型")
@Table(value = "sys_dict_type")
public class SysDictType extends BaseEntity {

    /**
     * 字典类型
     */
    @Schema(description = "字典类型")
    @Column(value = "TYPE")
    private String type;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    @Column(value = "NAME")
    private String name;

    /**
     * 字典状态，ENABLE启用DISABLE停用
     */
    @Schema(description = "字典状态，ENABLE启用DISABLE停用")
    @Column(value = "STATUS")
    private EnableStatusEnum status;
}
