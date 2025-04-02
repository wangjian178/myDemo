package com.wj.demo.core.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @ClassName SysUserPasswordVO
 * @Description: 修改密码VO
 * @Author: W.Jian
 * @CreateDate: 2025/4/2 15:13
 * @Version:
 */
@Data
@Schema(description = "修改密码VO")
public class SysUserPasswordVO {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String oldPassword;

    @Schema(description = "新密码")
    private String newPassword;
}
