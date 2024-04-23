package com.wj.demo.common.utils;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author wj
 * @version 1.0
 * @Desc Http工具类
 * @date 2024/4/23 15:33
 */
public class HttpUtils {

    private static RestTemplate template = null;

    public static RestTemplate getRestTemplate() {
        if (template == null) {
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setConnectTimeout(0);
            requestFactory.setReadTimeout(0);
            template = new RestTemplate(requestFactory);
        }
        return template;
    }

    /**
     * post请求 todo 加个切面 记录入参出参，url
     * @param url
     * @param param
     * @param clazz
     * @return
     * @param <T>
     */
    public <T> T post(String url, String param, Class<T> clazz) {
        return getRestTemplate().postForObject(url, param, clazz);
    }

}
