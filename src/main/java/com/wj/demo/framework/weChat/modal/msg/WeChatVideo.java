package com.wj.demo.framework.weChat.modal.msg;

import lombok.Data;

/**
 * @ClassName WeChatVideo
 * @Description: 视频
 * @Author: W.Jian
 * @CreateDate: 2025/3/4 18:10
 * @Version:
 */
@Data
public class WeChatVideo {
    /**
     * 视频媒体文件id
     */
    private String media_id;
    /**
     * 视频消息的标题
     */
    private String title;
    /**
     * 视频消息的描述
     */
    private String description;
}
