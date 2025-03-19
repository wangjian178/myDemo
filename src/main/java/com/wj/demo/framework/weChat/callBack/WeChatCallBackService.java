package com.wj.demo.framework.weChat.callBack;

import com.wj.demo.framework.weChat.modal.callBack.WeChatCallBackParam;
import com.wj.demo.framework.weChat.mp.aes.AesException;
import com.wj.demo.framework.weChat.mp.aes.WXBizJsonMsgCrypt;
import com.wj.demo.framework.weChat.property.WeChatProperties;
import com.wj.demo.framework.weChat.utils.WeChatUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ClassName WeChatCallBackService
 * @Description: 微信回调函数服务
 * @Author: W.Jian
 * @CreateDate: 2025/3/14 15:14
 * @Version:
 */
@Slf4j
@Service
public class WeChatCallBackService {

    @Resource
    private WeChatProperties weChatProperties;

    @Resource
    private WeChatUtils weChatUtils;


    /**
     * 校验回调函数
     *
     * @param weChatCallBackParam 回调函数参数
     */
    public void checkCallback(WeChatCallBackParam weChatCallBackParam) {
        try {
            WXBizJsonMsgCrypt wxBizJsonMsgCrypt = new WXBizJsonMsgCrypt(weChatUtils.getAccessToken(), weChatProperties.getEncodingAesKey(), weChatProperties.getCorpId());
            String sEchoStr = wxBizJsonMsgCrypt.VerifyURL(weChatCallBackParam.getMsg_signature(), weChatCallBackParam.getTimestamp(), weChatCallBackParam.getNonce(), weChatCallBackParam.getEchostr());
            log.debug("微信回调函数校验结果:{}", sEchoStr);
        } catch (AesException e) {
            log.error("微信回调函数校验失败:", e);
            throw new RuntimeException(e);
        }
    }
}
