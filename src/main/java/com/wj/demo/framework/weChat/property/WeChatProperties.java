package com.wj.demo.framework.weChat.property;

import lombok.Data;
import org.apache.ibatis.annotations.Property;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName WeChatProperties
 * @Description: 微信配置属性
 * @Author: W.Jian
 * @CreateDate: 2025/3/4 17:24
 * @Version:
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WeChatProperties {

    /**
     * 企业id
     */
    private String corpId;

    /**
     * 应用密钥
     */
    private String corpSecret;

    /**
     * 应用id
     */
    private String agentId;

    /**
     * 获取token的url
     */
    private String tokenUrl;

    /**
     * 发送消息的url
     */
    private String messageUrl;

    /**
     * 上传临时素材文件的url 3天内有效
     */
    private String uploadUrl;

    /**
     * 上传图片的url
     */
    private String uploadImageUrl;

    /**
     * 群机器人的url
     */
    private String robotUrl;
}
