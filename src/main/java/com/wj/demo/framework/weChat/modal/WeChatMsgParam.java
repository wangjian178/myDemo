package com.wj.demo.framework.weChat.modal;

import com.wj.demo.framework.weChat.modal.enums.WeChatMsgTypeEnum;
import com.wj.demo.framework.weChat.modal.msg.*;
import lombok.Data;

import java.io.File;

/**
 * @ClassName WeChatParam
 * @Description: 微信参数
 * @Author: W.Jian
 * @CreateDate: 2025/3/4 17:25
 * @Version:
 */
@Data
public class WeChatMsgParam {
    /**
     * 接收人 用|分开 @all发送给所有人
     */
    private String touser;

    /**
     * 部门接收人 用|分开 @all发送给所有人
     */
    private String toparty;

    /**
     * 标签接收人 用|分开 @all发送给所有人
     */
    private String totag;

    /**
     * 消息类型
     */
    private WeChatMsgTypeEnum msgtype;

    /**
     * 应用id
     */
    private Integer agentid;

    /**
     * 是否保密 默认0
     */
    private Integer safe;

    /**
     * 是否开启id转换 默认0
     */
    private Integer enable_id_trans;

    /**
     * 是否开启重复消息检查 默认0
     */
    private Integer enable_duplicate_check;

    /**
     * 重复消息检查的保留时长 默认1800
     */
    private Integer duplicate_check_interval;

    /**
     * 文本消息
     */
    private WeChatText text;

    /**
     * 图片消息
     */
    private WeChatImage image;

    /**
     * 语音消息
     */
    private WeChatVoice voice;

    /**
     * 视频消息
     */
    private WeChatVideo video;

    /**
     * 文件消息
     */
    private WeChatFile file;

    /**
     * 文本卡片消息
     */
    private WeChatTextCard textcard;

    /**
     * 图文消息
     */
    private WeChatNews news;

    /**
     * markdown消息
     */
    private WeChatMarkdown markdown;

    /**
     * 上传文件 用来发送图片或者图文
     */
    private File uploadFile;
}
