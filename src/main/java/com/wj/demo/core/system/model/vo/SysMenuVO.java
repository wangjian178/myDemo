package com.wj.demo.core.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @ClassName SysMenuVO
 * @Description: 菜单VO
 * @Author: W.Jian
 * @CreateDate: 2025/8/21 14:01
 * @Version:
 */
@Data
@Schema(name = "系统-菜单管理")
public class SysMenuVO {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;

    /**
     * 图标
     */
    @Schema(description = "图标")
    private String icon;

    /**
     * 路由
     */
    @Schema(description = "路由")
    private String path;

    /**
     * 组件
     */
    @Schema(description = "组件")
    private String component;

    /**
     * 是否隐藏
     */
    @Schema(description = "是否隐藏")
    private Boolean hidden;

    /**
     * 顺序
     */
    @Schema(description = "顺序")
    private Integer sortNo;

    /**
     * 父级ID
     */
    @Schema(description = "父级ID")
    private Long parentId;

    /**
     * 子菜单
     */
    @Schema(description = "子菜单")
    private List<SysMenuVO> children;
}
