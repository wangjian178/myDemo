package com.wj.demo.framework.weChat.modal.result;

import lombok.Data;

/**
 * @ClassName WeChatResult
 * @Description: 微信消息结果
 * @Author: W.Jian
 * @CreateDate: 2025/3/4 17:48
 * @Version:
 */
@Data
public class WeChatResult {

    public static final Integer SUCCESS = 0;

    /**
     * 错误码
     */
    private Integer errcode;
    /**
     * 错误信息
     */
    private String errmsg;

    /**
     * token 获取token接口用
     */
    private String access_token;
    /**
     * 过期时间（s） 获取token接口用
     */
    private Long expires_in;

    /**
     * 消息类型 上传临时素材类型
     */
    private String type;
    /**
     * 消息id 上传临时素材id
     */
    private String media_id;
    /**
     * 创建时间 上传临时素材时间 3天后过期
     */
    private String created_at;

    /**
     * 上传图片访问链接
     */
    private String url;
}
