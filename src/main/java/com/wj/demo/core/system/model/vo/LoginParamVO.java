package com.wj.demo.core.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @ClassName LoginVO
 * @Description: 登陆参数
 * @Author: W.Jian
 * @CreateDate: 2025/3/19 16:41
 * @Version:
 */
@Data
@Schema(description = "登陆参数")
public class LoginParamVO {
    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;
    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;
    /**
     * 验证码唯一标识
     */
    @Schema(description = "验证码唯一标识")
    private String captchaId;
    /**
     * 验证码
     */
    @Schema(description = "验证码")
    private String captchaCode;
}
