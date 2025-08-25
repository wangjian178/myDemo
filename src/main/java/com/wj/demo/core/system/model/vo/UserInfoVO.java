package com.wj.demo.core.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @ClassName UserInfoVO
 * @Description: 用户信息
 * @Author: W.Jian
 * @CreateDate: 2025/8/23 22:15
 * @Version:
 */
@Data
@Accessors(chain = true)
@Schema(description = "用户信息")
public class UserInfoVO {

    @Schema(description = "用户信息")
    private SysUserVO user;

    @Schema(description = "菜单列表")
    private List<SysMenuVO> menuList;

    @Schema(description = "角色列表")
    private List<SysRoleVO> roleList;
}
