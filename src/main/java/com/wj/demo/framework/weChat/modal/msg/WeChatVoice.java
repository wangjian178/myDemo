package com.wj.demo.framework.weChat.modal.msg;

import lombok.Data;

/**
 * @ClassName WeChatVoice
 * @Description: 语音
 * @Author: W.Jian
 * @CreateDate: 2025/3/4 18:09
 * @Version:
 */
@Data
public class WeChatVoice {
    /**
     * 语音文件id，可以调用上传临时素材接口获取
     */
    private String media_id;
}
