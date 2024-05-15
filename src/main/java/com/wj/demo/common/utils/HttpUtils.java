package com.wj.demo.common.utils;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author wj
 * @version 1.0
 * @Desc Http工具类
 * @date 2024/4/23 15:33
 */
public class HttpUtils {

    private static RestTemplate TEMPLATE = null;

    static {
        //初始化默认RestTemplate
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(0);
        requestFactory.setReadTimeout(0);
        TEMPLATE = new RestTemplate(requestFactory);
    }

    /**
     * post请求 todo 加个切面 记录入参出参，url
     *
     * @param url    地址
     * @param params 参数
     * @param clazz  返回类型
     * @param <T>    返回泛型
     * @return 返回值
     */
    public <T> T post(String url, Object params, Class<T> clazz) {
        return TEMPLATE.postForObject(url, params, clazz);
    }

    /**
     * get请求 无参数
     *
     * @param url   地址
     * @param clazz 返回类型
     * @param <T>   返回泛型
     * @return 返回值
     */
    public <T> T post(String url, Class<T> clazz) {
        return TEMPLATE.getForObject(url, clazz);
    }

    /**
     * get请求 带参数
     *
     * @param url    地址
     * @param clazz  返回类型
     * @param params 参数
     * @param <T>    返回泛型
     * @return 返回值
     */
    public <T> T post(String url, Class<T> clazz, Map<String, ?> params) {
        return TEMPLATE.getForObject(url, clazz, params);
    }
}
