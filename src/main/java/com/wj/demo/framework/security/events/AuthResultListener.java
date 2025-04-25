package com.wj.demo.framework.security.events;


import com.wj.demo.core.system.model.vo.LoginResultVO;
import com.wj.demo.core.system.service.ILoginService;
import com.wj.demo.framework.common.model.LoginUser;
import com.wj.demo.framework.common.utils.SpringContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @ClassName AuthResultListener
 * @Description: 认证结果监听
 * @Author: W.Jian
 * @CreateDate: 2025/4/15 16:09
 * @Version:
 */
@Component
public class AuthResultListener {

    private static final Logger log = LoggerFactory.getLogger(AuthResultListener.class);

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        log.info("认证成功 {}", ((LoginUser) event.getAuthentication().getPrincipal()).getUsername());
        //todo 记录日志
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent event) {
        log.error("认证失败 ",event.getException());

        //todo 记录日志
    }
}
