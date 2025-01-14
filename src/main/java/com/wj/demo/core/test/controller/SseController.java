package com.wj.demo.core.test.controller;

import com.wj.demo.framework.sseEmitter.utils.SseUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author W.Jian
 * @version 1.0
 * @Desc
 * @date 2025/1/14 17:33
 */
@RestController
@RequestMapping("/sse")
public class SseController {

    @RequestMapping("/open/{userId}")
    public void open(@PathVariable Long userId) {
        SseUtils.open(userId);
    }

    @RequestMapping("/send/{userId}")
    public void send(@PathVariable Long userId, @RequestParam("message") String message) {
        SseUtils.sendMessage(userId, message);
    }

    @RequestMapping("/close/{userId}")
    public void close(@PathVariable Long userId) {
        SseUtils.close(userId);
    }
}
