package com.wj.demo.framework.weChat.callBack;

import com.wj.demo.framework.weChat.modal.callBack.WeChatCallBackParam;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName WeChatCallBackController
 * @Description: 微信回调函数
 * @Author: W.Jian
 * @CreateDate: 2025/3/14 15:07
 * @Version:
 */
@Schema(description = "微信回调函数")
@RequestMapping("/wechat")
@RestController
public class WeChatCallBackController {

    @Resource
    private WeChatCallBackService weChatCallBackService;

    @Schema(description = "校验回调函数")
    @GetMapping("/checkCallback")
    public void checkCallback(WeChatCallBackParam weChatCallBackParam) {
        weChatCallBackService.checkCallback(weChatCallBackParam);
    }

    @Schema(description = "校验回调函数")
    @PostMapping("/aa")
    public void aa(WeChatCallBackParam weChatCallBackParam, HttpServletResponse response) {

    }
}
