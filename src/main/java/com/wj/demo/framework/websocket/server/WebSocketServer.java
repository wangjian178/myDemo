package com.wj.demo.framework.websocket.server;

import com.wj.demo.framework.common.utils.SecurityUtils;
import com.wj.demo.framework.websocket.config.WebSocketSessionPool;
import com.wj.demo.framework.websocket.model.WebSocketSender;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author W.Jian
 * id区分页面 token区分用户
 * @version 1.0
 * @Desc WebSocketServer
 * @date 2025/1/10 15:35
 */
@ServerEndpoint("/ws/{chanel}/{token}")
@Component
public class WebSocketServer {

    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    //用来记录当前在线连接数。应该把它设计成线程安全的。
    private static final AtomicInteger ON_LINE_NUM = new AtomicInteger(0);

    @OnOpen
    public void onOpen(Session session,
                       @PathParam(value = "chanel") String chanel,
                       @PathParam(value = "token") String token) {
        int onLineNum = ON_LINE_NUM.incrementAndGet();
        WebSocketSessionPool.add(session);
        session.getUserProperties().put("chanel", chanel);
        session.getUserProperties().put("userId", SecurityUtils.getUser(token).getId().toString());
        log.info("连接加入，当前连接数为：{}，chanel为：{}", onLineNum, chanel);
    }

    @OnClose
    public void onClose(Session session) {
        int onLineNum = ON_LINE_NUM.decrementAndGet();
        WebSocketSessionPool.remove(session);
        log.info("连接断开，当前连接数为：{}", onLineNum);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("ws异常：{}", throwable.toString());
    }

    /**
     * 接收消息
     * 可以有返回值
     *
     * @param session 接收者
     * @param message 消息内容 类型=String、byte[]、ByteBuffer、Reader、InputStream
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        log.info("接收到消息：{}", message);
        sendMessage(session, "收到消息：" + message);
    }

    /**
     * 同步发送消息
     *
     * @param session 接收者
     * @param message 消息内容
     */
    private void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("发送消息出错：{}", e.getMessage());
        }
    }

    /**
     * 异步发送消息
     *
     * @param session 接收者
     * @param message 消息内容
     */
    private void sendAsyncMessage(Session session, String message) {
        session.getAsyncRemote().sendText(message);
    }

    /**
     * 发送消息
     *
     * @param sender 消息
     */
    public void sendMessage(WebSocketSender sender) {
        for (Session session : WebSocketSessionPool.sessionPool()) {
            //发送频道不同
            if (!session.getUserProperties().get("chanel").equals(sender.getChanel())) {
                continue;
            }
            //不发送全部 并且 接收人不包含当前用户
            List<String> receiver = CollectionUtils.isEmpty(sender.getReceiver()) ? new ArrayList<>() : sender.getReceiver();
            if (sender.getSendAll() == null || !sender.getSendAll() && !receiver.contains((String) session.getUserProperties().get("userId"))) {
                continue;
            }
            if (sender.getAsync() != null && sender.getAsync()) {
                sendAsyncMessage(session, sender.getMessage());
            } else {
                sendMessage(session, sender.getMessage());
            }
        }
    }
}

