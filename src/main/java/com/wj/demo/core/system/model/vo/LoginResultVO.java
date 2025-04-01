package com.wj.demo.core.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @ClassName LoginResultVO
 * @Description: 登录信息VO
 * @Author: W.Jian
 * @CreateDate: 2025/3/27 14:56
 * @Version:
 */
@Data
@Schema(description = "登录信息VO")
public class LoginResultVO {

    @Schema(description = "token")
    private String token;

    @Schema(description = "过期时间")
    private Long expireTime;

    @Schema(description = "时间戳")
    private Long timestamp;
}
