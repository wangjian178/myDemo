package com.wj.demo.framework.common.aspect;

import com.wj.demo.framework.common.annotation.CheckReferer;
import com.wj.demo.framework.common.property.AllowedReferer;
import com.wj.demo.framework.common.utils.ServletUtils;
import com.wj.demo.framework.common.utils.SpringContextUtils;
import com.wj.demo.framework.common.utils.StringUtils;
import com.wj.demo.framework.exception.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @ClassName CheckRefererAspect
 * @Description: 验证请求来源
 * @Author: W.Jian
 * @CreateDate: 2025/8/14 10:11
 * @Version: 1.0
 */
@Slf4j
@Aspect
@Component
public class CheckRefererAspect {

    @Before(value = "@annotation(checkReferer)", argNames = "joinPoint,checkReferer")
    public void before(JoinPoint joinPoint, CheckReferer checkReferer) {
        HttpServletRequest request = ServletUtils.getRequest();
        String referer = request.getHeader("referer");

        if (checkReferer.enabled() && StringUtils.isNotEmpty(referer) && Arrays.stream(SpringContextUtils.getBean(AllowedReferer.class).getReferer()).noneMatch(allowedReferer -> allowedReferer.equals(referer))) {
            log.error("非法请求：{}", referer);
            throw new BusinessException("非法请求");
        }
    }
}
