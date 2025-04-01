package com.wj.demo.core.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @ClassName LogoutResultVO
 * @Description: 登出结果
 * @Author: W.Jian
 * @CreateDate: 2025/4/1 14:15
 * @Version:
 */
@Data
@Schema(description = "登出信息VO")
public class LogoutResultVO {

    @Schema(description = "状态")
    private boolean status;

    @Schema(description = "重定向地址")
    private String redirectUrl;
}
