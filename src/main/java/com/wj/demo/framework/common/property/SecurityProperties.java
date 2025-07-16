package com.wj.demo.framework.common.property;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wj
 * @version 1.0
 * @Desc 登录接口
 */
@Getter
@Setter
public class SecurityProperties {

    /**
     * 登录处理器
     */
    private String loginHandler;

    /**
     * 登出处理器
     */
    private String logoutHandler;

    /**
     * 登陆页面地址
     */
    private String loginUrl;

    /**
     * 登陆页面地址
     */
    private String loginPage;

    /**
     * 登出页面地址
     */
    private String logoutUrl;

    /**
     * 过期时间 s
     */
    private Long expireTime;

    /**
     * 密钥
     */
    private String secretKey;

    /**
     * 认证
     */
    private AuthProperties auth;
}