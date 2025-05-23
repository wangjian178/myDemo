package com.wj.demo.framework.security;

import com.alibaba.fastjson.JSON;
import com.wj.demo.core.system.model.vo.LoginResultVO;
import com.wj.demo.core.system.service.ILoginService;
import com.wj.demo.framework.common.model.LoginUser;
import com.wj.demo.framework.common.utils.SpringContextUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * @ClassName AuthenticationSuccessHandlerImpl
 * @Description: 认证成功处理器
 * @Author: W.Jian
 * @CreateDate: 2025/4/15 13:55
 * @Version:
 */
public class MyAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        ILoginService service = SpringContextUtils.getBean(ILoginService.class);
        LoginResultVO loginResultVO = service.createToken(loginUser);
        response.getWriter().print(JSON.toJSONString(loginResultVO));
    }
}
