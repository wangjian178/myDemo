package com.wj.demo.common.utils;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author wj
 * @version 1.0
 * @Desc
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

    public <T> T post(String url, String param, Class<T> clazz) {
        return getRestTemplate().postForObject(url, param, clazz);
    }

}
