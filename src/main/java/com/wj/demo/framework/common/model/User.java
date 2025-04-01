package com.wj.demo.framework.common.model;

import com.wj.demo.core.system.enums.UserStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/25 17:41
 */
@Data
@Accessors(chain = true)
@Schema(description = "用户")
public class User {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "状态 0下线 1在线")
    private UserStatusEnum status;
}
