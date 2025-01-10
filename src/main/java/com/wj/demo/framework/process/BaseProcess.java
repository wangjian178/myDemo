package com.wj.demo.framework.process;

/**
 * @author wj
 * @version 1.0
 * @Desc 定义的流程
 * 所有流程继承的父类
 * 相对于随时建立，固定的流程显然更适合处理业务
 * @date 2024/6/11 17:58
 */
public class BaseProcess<T> {

    private Long id;

    private String code;

    private String desc;

    private T param;

    /**
     * 核心处理方法
     *
     * @param param
     */
    public void handle(T param) {

    }
}
