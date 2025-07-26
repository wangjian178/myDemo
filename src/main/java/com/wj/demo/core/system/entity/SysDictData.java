package com.wj.demo.core.system.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.wj.demo.core.system.enums.DictStyleEnum;
import com.wj.demo.core.system.enums.EnableStatusEnum;
import com.wj.demo.framework.mybatisFlex.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统-字典项 实体类。
 *
 * @author wj
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data
@Schema(name = "系统-字典项")
@Table(value = "sys_dict_data")
public class SysDictData extends BaseEntity {

    /**
     * 字典类型
     */
    @Schema(description = "字典类型")
    @Column(value = "DICT_TYPE")
    private String dictType;

    /**
     * 字典项名称
     */
    @Schema(description = "字典项名称")
    @Column(value = "DICT_LABEL")
    private String dictLabel;

    /**
     * 字典项值
     */
    @Schema(description = "字典项值")
    @Column(value = "DICT_VALUE")
    private String dictValue;

    /**
     * 顺序
     */
    @Schema(description = "顺序")
    @Column(value = "SORT")
    private Integer sort;

    /**
     * 样式
     */
    @Schema(description = "样式")
    @Column(value = "STYLE")
    private DictStyleEnum style;

    /**
     * 启用状态ENABLE启用DISABLE停用
     */
    @Schema(description = "启用状态ENABLE启用DISABLE停用")
    @Column(value = "STATUS")
    private EnableStatusEnum status;

    /**
     * 是否默认值0否1是
     */
    @Schema(description = "是否默认值0否1是")
    @Column(value = "IS_DEFAULT")
    private Integer isDefault;

    /**
     * 父级字典值
     */
    @Schema(description = "父级字典值")
    @Column(value = "PARENT_VALUE")
    private String parentValue;
}
