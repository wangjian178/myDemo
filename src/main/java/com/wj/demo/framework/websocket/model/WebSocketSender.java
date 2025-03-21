package com.wj.demo.framework.websocket.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author W.Jian
 * @version 1.0
 * @Desc ws消息
 * @date 2025/1/10 16:31
 */
@Data
@Accessors(chain = true)
public class WebSocketSender implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 消息类型
     */
    private String type;

    /**
     * 是否异步
     */
    private Boolean async;

    /**
     * 是否异步
     */
    private Boolean sendAll;

    /**
     * 消息频道
     */
    private String chanel;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 消息接收者
     */
    private List<String> receiver;
}
