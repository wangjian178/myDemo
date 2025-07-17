package com.wj.demo.framework.security.handler;

import com.alibaba.fastjson.JSON;
import com.wj.demo.framework.exception.model.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 * @ClassName MyAuthFailureHandler
 * @Description: 登陆失败处理器
 * @Author: W.Jian
 * @CreateDate: 2025/4/15 15:51
 * @Version:
 */
public class MyAuthFailureHandler implements AuthenticationFailureHandler {

    private static final Logger log = LoggerFactory.getLogger(MyAuthFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(JSON.toJSONString(Result.ofFail(exception.getMessage())));
    }
}
