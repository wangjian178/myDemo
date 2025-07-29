package com.wj.demo.core.system.service.impl;


import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wj.demo.core.system.entity.SysScheduleJob;
import com.wj.demo.core.system.mapper.SysScheduleJobMapper;
import com.wj.demo.core.system.service.ISysScheduleJobService;
import com.wj.demo.framework.common.utils.SpringContextUtils;
import com.wj.demo.framework.exception.exception.BaseException;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * 系统-定时任务表 服务层实现。
 *
 * @author wj
 * @since 1.0.0
 */
@Service
public class SysScheduleJobServiceImpl extends ServiceImpl<SysScheduleJobMapper, SysScheduleJob> implements ISysScheduleJobService {

    /**
     * 添加系统-定时任务表
     *
     * @param sysScheduleJob 系统-定时任务表
     */
    @Override
    public void add(SysScheduleJob sysScheduleJob) {

    }

    /**
     * 执行系统-定时任务表
     *
     * @param sysScheduleJob 系统-定时任务表
     */
    @Override
    public void execute(SysScheduleJob sysScheduleJob) {
        String jobClass = sysScheduleJob.getJobClass();
        //反射创建类的对象
        try {
            Class<?> clazz = Class.forName(jobClass);
            Method[] methods = clazz.getDeclaredMethods();
            Method method = Arrays.stream(methods).filter(x -> x.getName().equals(sysScheduleJob.getMethod())).findFirst().orElseThrow(() -> new BaseException("方法" + sysScheduleJob.getName() + "不存在"));
            //判断是否是静态方法
            if (Modifier.isStatic(method.getModifiers())) {
                //执行静态方法
                method.invoke(null, sysScheduleJob.getParams());
            } else {
                //获取bean
                Object bean = SpringContextUtils.getBean(clazz);
                if (bean == null) {
                    bean = clazz.getDeclaredConstructor().newInstance();
                }
                method.invoke(bean, sysScheduleJob.getParams());
            }
        } catch (ClassNotFoundException
                 | InvocationTargetException
                 | InstantiationException
                 | IllegalAccessException
                 | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}