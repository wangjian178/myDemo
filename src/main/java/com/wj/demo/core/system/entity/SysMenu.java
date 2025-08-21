package com.wj.demo.core.system.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.wj.demo.framework.mybatisFlex.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统-菜单管理
 *
 * @author wj
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data
@Schema(name = "系统-菜单管理")
@Table(value = "sys_menu")
public class SysMenu extends BaseEntity {

    /**
     * 标题
     */
    @Schema(description = "标题")
    @Column(value = "TITLE")
    private String title;

    /**
     * 图标
     */
    @Schema(description = "图标")
    @Column(value = "ICON")
    private String icon;

    /**
     * 路由
     */
    @Schema(description = "路由")
    @Column(value = "PATH")
    private String path;

    /**
     * 组件
     */
    @Schema(description = "组件")
    @Column(value = "COMPONENT")
    private String component;

    /**
     * 是否隐藏
     */
    @Schema(description = "是否隐藏")
    @Column(value = "HIDDEN")
    private Boolean hidden;

    /**
     * 顺序
     */
    @Schema(description = "顺序")
    @Column(value = "SORT_NO")
    private Integer sortNo;

    /**
     * 父级ID
     */
    @Schema(description = "父级ID")
    @Column(value = "PARENT_ID")
    private Long parentId;

    /**
     * 子系统ID
     */
    @Schema(description = "子系统ID")
    @Column(value = "SUB_SYS_ID")
    private Long subSysId;
}
