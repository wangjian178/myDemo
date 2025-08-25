package com.wj.demo.core.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName SysRoleVO
 * @Description: 角色
 * @Author: W.Jian
 * @CreateDate: 2025/8/24 21:21
 * @Version:
 */
@Data
@Accessors(chain = true)
@Schema(description = "角色")
public class SysRoleVO {

    @Schema(description = "角色ID")
    private Long id;

    @Schema(description = "角色编码")
    private String roleCode;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色描述")
    private String roleDesc;
}
