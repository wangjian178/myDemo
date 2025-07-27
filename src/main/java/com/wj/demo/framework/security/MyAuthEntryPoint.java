package com.wj.demo.framework.security;

import com.alibaba.fastjson.JSON;
import com.wj.demo.core.system.service.ITokenService;
import com.wj.demo.framework.common.utils.SpringContextUtils;
import com.wj.demo.framework.common.utils.StringUtils;
import com.wj.demo.framework.exception.model.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

/**
 * @ClassName AuthenticationEntryPointImpl
 * @Description: 鉴权切入点
 * @Author: W.Jian
 * @CreateDate: 2025/4/15 10:51
 * @Version:
 */
public class MyAuthEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Serial
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ITokenService tokenService = SpringContextUtils.getBean(ITokenService.class);
        String token = tokenService.getToken(request);

        if (StringUtils.isEmpty(token) || tokenService.getLoginUser(token) == null) {
            //返回信息
            String msg = String.format("请求访问：%s，认证失败，无法访问系统资源", request.getRequestURI());
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(JSON.toJSONString(Result.ofFail(String.valueOf(HttpStatus.FORBIDDEN.value()), msg)));
        }
    }
}
