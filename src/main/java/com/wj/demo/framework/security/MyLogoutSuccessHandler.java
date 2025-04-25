package com.wj.demo.framework.security;

import com.alibaba.fastjson.JSON;
import com.wj.demo.framework.exception.model.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

/**
 * @ClassName LogoutSuccessHandlerImpl
 * @Description: 登出成功处理器
 * @Author: W.Jian
 * @CreateDate: 2025/4/15 11:27
 * @Version:
 */
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //返回信息
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(JSON.toJSONString(Result.ofSuccess("退出成功")));
    }
}
