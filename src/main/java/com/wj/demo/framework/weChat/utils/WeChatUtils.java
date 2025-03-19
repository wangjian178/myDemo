package com.wj.demo.framework.weChat.utils;

import com.alibaba.fastjson.JSON;
import com.wj.demo.framework.redis.service.RedisClient;
import com.wj.demo.framework.weChat.modal.WeChatMsgParam;
import com.wj.demo.framework.weChat.modal.result.WeChatResult;
import com.wj.demo.framework.weChat.property.WeChatProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName WeChatServer
 * @Description: 微信服务 todo 后续优化支持多个微信号
 * @Author: W.Jian
 * @CreateDate: 2025/3/4 17:26
 * @Version:
 */
@Slf4j
@Component
public class WeChatUtils {

    private static final String ACCESS_TOKEN_KEY = "wechat:access_token:";

    @Resource
    private WeChatProperties weChatProperties;

    @Resource
    private RedisClient redisClient;

    @Resource
    private RestTemplate restTemplate;

    /**
     * 获取token
     *
     * @return token
     */
    public String getAccessToken() {
        String key = ACCESS_TOKEN_KEY + weChatProperties.getCorpId();
        if (redisClient.exists(key)) {
            return redisClient.get(key);
        }
        //请求获取token
        String url = String.format(weChatProperties.getTokenUrl(), weChatProperties.getCorpId(), weChatProperties.getCorpSecret());
        WeChatResult weChatResult = restTemplate.getForObject(url, WeChatResult.class);
        //设置token到redis中
        redisClient.set(key, weChatResult.getAccess_token(), weChatResult.getExpires_in(), TimeUnit.SECONDS);
        return weChatResult.getAccess_token();
    }

    /**
     * 上传临时素材
     */
    public WeChatResult uploadMedia(File file, String type) {
        String url = String.format(weChatProperties.getUploadUrl(), getAccessToken(), type);
        // 创建 MultiValueMap 来存放表单数据
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("filename", new FileSystemResource(file));
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA);
        // 创建 HttpEntity 对象
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        //发送消息
        return restTemplate.postForObject(url, requestEntity, WeChatResult.class);
    }

    /**
     * 上传图片
     */
    public WeChatResult uploadPic(File file) {
        String url = String.format(weChatProperties.getUploadImageUrl(), getAccessToken());
        // 创建 MultiValueMap 来存放表单数据
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("filename", new FileSystemResource(file));
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        //发送消息
        return restTemplate.postForObject(url, requestEntity, WeChatResult.class);
    }

    /**
     * 微信机器人发送消息
     *
     * @param param 消息
     * @return 结果
     */
    public WeChatResult sendRobotMsg(WeChatMsgParam param) {
        log.debug("微信机器人发送消息:{}", JSON.toJSONString(param));
        return restTemplate.postForObject(weChatProperties.getRobotUrl(), param, WeChatResult.class);
    }

    /**
     * 发送应用消息
     * todo待验证 需要配置可信ip
     *
     * @param param 消息
     * @return 结果
     */
    public WeChatResult sendAppMsg(WeChatMsgParam param) {
        log.debug("微信发送应用消息:{}", JSON.toJSONString(param));
        String url = String.format(weChatProperties.getMessageUrl(), getAccessToken());
        return restTemplate.postForObject(url, param, WeChatResult.class);
    }
}
