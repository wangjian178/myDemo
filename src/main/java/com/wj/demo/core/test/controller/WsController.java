package com.wj.demo.core.test.controller;

import com.wj.demo.framework.ws.model.WebSocketSender;
import com.wj.demo.framework.ws.server.WebSocketServer;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author W.Jian
 * @version 1.0
 * @Desc
 * @date 2025/1/10 16:44
 */
@RestController
@RequestMapping("/ws")
public class WsController {

    @Resource
    private WebSocketServer webSocketServer;

    @PostMapping("/send")
    public void sendMessage(@RequestBody WebSocketSender sender) {
        webSocketServer.sendMessage(sender);
    }

}
