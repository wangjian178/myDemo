package com.wj.demo.framework.weChat.modal.msg;

import lombok.Data;

/**
 * @ClassName WeChatTextcard
 * @Description: 卡片
 * @Author: W.Jian
 * @CreateDate: 2025/3/4 18:11
 * @Version:
 */
@Data
public class WeChatTextCard {
    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 点击后跳转的链接
     */
    private String url;

    /**
     * 按钮文字
     */
    private String btntxt;


}
