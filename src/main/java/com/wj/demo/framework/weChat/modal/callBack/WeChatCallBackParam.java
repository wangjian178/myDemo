package com.wj.demo.framework.weChat.modal.callBack;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @ClassName WeChatCallBackParam
 * @Description: 微信回调函数参数
 * @Author: W.Jian
 * @CreateDate: 2025/3/14 15:11
 * @Version:
 */
@Schema(description = "微信回调函数参数")
@Data
public class WeChatCallBackParam {

    @Schema(description = "加密签名")
    private String msg_signature;

    @Schema(description = "时间戳")
    private String timestamp;

    @Schema(description = "随机字符串 对应URL参数的nonce")
    private String nonce;

    @Schema(description = "随机字符串 对应URL参数的echostr")
    private String echostr;

    @Schema(description = "加密字符串")
    private String encrypt;
}
