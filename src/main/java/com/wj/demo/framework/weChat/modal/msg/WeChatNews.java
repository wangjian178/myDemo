package com.wj.demo.framework.weChat.modal.msg;

import lombok.Data;

import java.util.List;

/**
 * @ClassName WeChatNews
 * @Description: 图文消息
 * @Author: W.Jian
 * @CreateDate: 2025/3/4 18:12
 * @Version:
 */
@Data
public class WeChatNews {

    /**
     * 图文消息列表
     */
    private List<Article> articles;

    @Data
    public static class Article {
        /**
         * 标题
         */
        private String title;
        /**
         * 描述
         */
        private String description;
        /**
         * 跳转链接
         */
        private String url;
        /**
         * 图片链接
         */
        private String picurl;
        /**
         * 跳转小程序的appid
         */
        private String appid;
        /**
         * 跳转小程序的页面路径
         */
        private String pagepath;
    }
}
