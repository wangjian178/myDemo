package com.wj.demo.i18n.aspact;

import com.wj.demo.i18n.CommonMessageSource;
import com.wj.demo.i18n.entity.LanguageMessageEntity;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 * @Desc 更新本地缓存切面
 * @date 2024/4/30 11:24
 */
@Slf4j
@Aspect
@Component
public class LocalCacheAspect {

    @Resource
    private CommonMessageSource commonMessageSource;

    @Pointcut("execution(public int com.wj.demo.i18n.service.LanguageMessageService.saveOrUpdateBatch(java.util.List<com.wj.demo.i18n.entity.LanguageMessageEntity>))")
    public void updateLocalCachePointcut() {

    }

    /**
     * 更新本地缓存 todo 当前方案为临时方案 后面考虑查询的时候先写入本地缓存，再写入redis，最后数据库 同时删除的时候先删除本地缓存，再删除redis，最后删除数据库
     * @param joinPoint
     * @return
     */
    @Around("updateLocalCachePointcut()")
    public int updateLocalCache(ProceedingJoinPoint joinPoint) {
        Object[] params = joinPoint.getArgs();
        List<LanguageMessageEntity> param = (List<LanguageMessageEntity>) params[0];
        int result = 0;
        try {
            result = (int) joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        //更新本地缓存
        commonMessageSource.saveOrUpdateLocalCacheBatch(param);
        return result;
    }
}
