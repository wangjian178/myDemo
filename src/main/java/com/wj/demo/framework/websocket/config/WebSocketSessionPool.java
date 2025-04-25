package com.wj.demo.framework.websocket.config;

import com.wj.demo.framework.common.model.LoginUser;
import com.wj.demo.framework.common.utils.SecurityUtils;
import jakarta.websocket.Session;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ClassName WebSocketSession
 * @Description: ws连接
 * @Author: W.Jian
 * @CreateDate: 2025/4/15 11:08
 * @Version:
 */
public class WebSocketSessionPool {

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    private static final Set<Session> SESSION_POOL = new CopyOnWriteArraySet<>();

    public static void add(Session session) {
        SESSION_POOL.add(session);
    }

    public static void remove(Session session) {
        SESSION_POOL.remove(session);
    }

    /**
     * 通过TOKEN删除用户连接
     * @param token token
     */
    public static void removeByToken(String token) {
        LoginUser user = SecurityUtils.getUser(token);
        if (user == null) {
            return;
        }
        String userId = SecurityUtils.getUser(token).getId().toString();
        SESSION_POOL.removeIf(session -> userId.equals(session.getUserProperties().get("userId")));
    }

    public static Set<Session> sessionPool() {
        return SESSION_POOL;
    }
}
