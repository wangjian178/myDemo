package com.wj.demo.core.test.controller;

import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/5/15 16:27
 */
@RestController
@RequestMapping("mq")
public class MQController {

    @Resource
    private RabbitTemplate rabbitTemplate;


    @PostMapping("send")
    public void send(){
        for (int i = 0; i < 10; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("index",i);
            rabbitTemplate.convertAndSend(jsonObject);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
