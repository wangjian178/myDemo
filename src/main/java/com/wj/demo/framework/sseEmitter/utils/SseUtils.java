package com.wj.demo.framework.sseEmitter.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author W.Jian
 * @version 1.0
 * @Desc sse工具类
 *
 * <p color='green'>适用于此类场景：</p>
 * <p color='green'>1.实时新闻更新：新闻发布后，立即推送给所有用户。</p>
 * <p color='green'>2.实时股票信息：股票信息变化时，更新给所有用户。</p>
 * <p color='green'>3.实时天气信息：天气更新时，推送给需要的用户。</p>
 * <p color='green'>4.在线用户列表：服务器端监控在线用户，可以实时推送在线用户列表。</p>
 * <p color='green'>5.实时数据监控仪表板：监控数据变化时，实时推送给管理员。</p>
 * @date 2025/1/14 16:38
 */
@Slf4j
public class SseUtils<T> {
    /**
     * 消息发送池
     */
    private static final Map<Long, SseEmitter> SENDER_POOL = new ConcurrentHashMap<>();


    /**
     * 发送消息
     *
     * @param userId  用户id
     * @param message 消息内容
     * @param <T>     泛型
     */
    public static <T> void sendMessage(Long userId, T message) {
        if (userId == null || !SENDER_POOL.containsKey(userId)) {
            return;
        }
        //获取发送器
        SseEmitter sseEmitter = SENDER_POOL.get(userId);
        try {
            sseEmitter.send(
                    SseEmitter.event()
                            .id(String.valueOf(userId))
                            .name("message")
                            .comment("comment")
                            .data(message)
                            .reconnectTime(5000)
            );
        } catch (IOException e) {
            SENDER_POOL.remove(userId);
            log.error("sse sendMessage error:{}", String.valueOf(e));
        }
    }

    /**
     * 打开连接
     *
     * @param userId 用户ID
     */
    public static void open(Long userId) {
        if (userId == null) {
            return;
        }
        SseEmitter emitter = new SseEmitter();
        emitter.onCompletion(() -> SENDER_POOL.remove(userId));
        emitter.onTimeout(() -> SENDER_POOL.remove(userId));
        SENDER_POOL.put(userId, emitter);
    }


    /**
     * 关闭连接
     *
     * @param userId 用户id
     */
    public static void close(Long userId) {
        if (userId == null || !SENDER_POOL.containsKey(userId)) {
            return;
        }
        SseEmitter sseEmitter = SENDER_POOL.remove(userId);
        sseEmitter.complete();
    }
}
