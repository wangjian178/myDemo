package com.wj.demo.core.system.aspect;

import com.alibaba.fastjson.JSON;
import com.wj.demo.core.system.context.OperateLogContext;
import com.wj.demo.core.system.entity.SysOperateLog;
import com.wj.demo.core.system.service.ISysOperateLogService;
import com.wj.demo.framework.common.annotation.OperateLog;
import com.wj.demo.framework.common.constant.NumberConstant;
import com.wj.demo.framework.common.utils.ServletUtils;
import com.wj.demo.framework.common.utils.SpringContextUtils;
import com.wj.demo.framework.common.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 * @ClassName OperateLogAspect
 * @Description: 操作日志
 * @Author: W.Jian
 * @CreateDate: 2025/7/18 13:33
 * @Version:
 */
@Slf4j
@Aspect
@Component
public class OperateLogAspect {

    @Before(value = "@annotation(operateLog)", argNames = "joinPoint,operateLog")
    public void before(JoinPoint joinPoint, OperateLog operateLog) {
        SysOperateLog sysOperateLog = buildOperateLog(operateLog, joinPoint);
        OperateLogContext.set(sysOperateLog);
    }

    @AfterReturning(value = "@annotation(operateLog)", returning = "result", argNames = "joinPoint,operateLog,result")
    public void afterReturning(JoinPoint joinPoint, OperateLog operateLog, Object result) {
        SysOperateLog sysOperateLog = OperateLogContext.get();
        sysOperateLog.setResult(StringUtils.substring(JSON.toJSONString(result), NumberConstant.ZERO, NumberConstant.FIVE_HUNDREDS));
        saveOperateLog(sysOperateLog);
    }

    @AfterThrowing(value = "@annotation(operateLog)", throwing = "exception", argNames = "joinPoint,operateLog,exception")
    public void afterThrowing(JoinPoint joinPoint, OperateLog operateLog, Exception exception) {
        SysOperateLog sysOperateLog = OperateLogContext.get();
        sysOperateLog.setException(StringUtils.substring(exception.getMessage(), NumberConstant.ZERO, NumberConstant.FIVE_HUNDREDS));
        saveOperateLog(sysOperateLog);
    }


    /**
     * 构建操作日志
     */
    private SysOperateLog buildOperateLog(OperateLog operateLog, JoinPoint joinPoint) {
        HttpServletRequest request = ServletUtils.getRequest();
        String ip = request.getRemoteAddr();
        String url = request.getRequestURI();
        String requestMethod = request.getMethod();

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        SysOperateLog log = new SysOperateLog();
        log.setModule(operateLog.module());
        log.setFunction(operateLog.function());
        log.setOperateType(operateLog.operateType());
        log.setMethod(className + "." + methodName);
        log.setRequestMethod(requestMethod);
        log.setIp(ip);
        log.setUrl(url);
        log.setOperateTime(LocalDateTime.now());

        if (operateLog.saveParams()) {
            if (RequestMethod.GET.name().equals(requestMethod)) {
                log.setParams(StringUtils.substring(JSON.toJSONString(request.getParameterMap()), NumberConstant.ZERO, NumberConstant.FIVE_HUNDREDS));
            } else if (joinPoint.getArgs().length > 0 && joinPoint.getArgs()[0] instanceof MultipartFile) {
                log.setParams(StringUtils.substring(((MultipartFile) joinPoint.getArgs()[0]).getOriginalFilename(), NumberConstant.ZERO, NumberConstant.FIVE_HUNDREDS));
            } else if (joinPoint.getArgs().length > 0) {
                log.setParams(StringUtils.substring(JSON.toJSONString(joinPoint.getArgs()[0]), NumberConstant.ZERO, NumberConstant.FIVE_HUNDREDS));
            }
        }

        return log;
    }

    /**
     * 保存操作日志
     */
    private static void saveOperateLog(SysOperateLog sysOperateLog) {
        // 异步保存
        SpringContextUtils.getBean(ThreadPoolTaskExecutor.class).execute(() -> {
            SpringContextUtils.getBean(ISysOperateLogService.class).save(sysOperateLog);
        });
        // 清除线程变量
        OperateLogContext.clear();
    }
}
