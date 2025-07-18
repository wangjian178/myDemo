package com.wj.demo.core.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName CaptchaVO
 * @Description: 验证码
 * @Author: W.Jian
 * @CreateDate: 2025/7/18 10:47
 * @Version:
 */
@Data
@Accessors(chain = true)
public class CaptchaVO {
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
    /**
     * 验证码图片
     */
    @Schema(description = "验证码图片")
    private String base64Img;
    /**
     * 过期时间 s
     */
    @Schema(description = "过期时间")
    private Long expireTime;
}
