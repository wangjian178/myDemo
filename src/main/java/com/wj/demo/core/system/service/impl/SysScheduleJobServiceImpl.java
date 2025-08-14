package com.wj.demo.core.system.service.impl;


import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wj.demo.core.system.entity.SysScheduleJob;
import com.wj.demo.core.system.enums.JobStatusEnum;
import com.wj.demo.core.system.mapper.SysScheduleJobMapper;
import com.wj.demo.core.system.service.ISysScheduleJobService;
import com.wj.demo.framework.common.utils.SpringContextUtils;
import com.wj.demo.framework.common.utils.StringUtils;
import com.wj.demo.framework.common.utils.ThreadUtils;
import com.wj.demo.framework.exception.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 系统-定时任务表 服务层实现。
 * todo 增加一个补偿机制 异常宕机的情况下，开机重新载入
 *
 * @author wj
 * @since 1.0.0
 */
@Slf4j
@Service
public class SysScheduleJobServiceImpl extends ServiceImpl<SysScheduleJobMapper, SysScheduleJob> implements ISysScheduleJobService {

    /**
     * 定时任务列表 todo 考虑分布式要共用一个任务池 即公用缓存
     */
    private static final List<Long> JOB_POOL = Collections.synchronizedList(new ArrayList<>());

    /**
     * 添加系统-定时任务表
     *
     * @param sysScheduleJob 系统-定时任务表
     */
    @Override
    public void add(SysScheduleJob sysScheduleJob) {
        save(sysScheduleJob);
        delayExecute(sysScheduleJob);
    }

    /**
     * 延迟执行
     *
     * @param sysScheduleJob 系统-延迟定时任务
     */
    @Override
    public void delayExecute(SysScheduleJob sysScheduleJob) {
        //延迟时间=触发时间-当前时间
        long delayMs = sysScheduleJob.getTriggerTime().toInstant(ZoneOffset.of("+8")).toEpochMilli() - LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();

        //添加到任务池
        JOB_POOL.add(sysScheduleJob.getId());

        //开启新线程运行
        ThreadUtils.getScheduler().schedule(() -> {
            //执行
            execute(sysScheduleJob);
            //从任务池转移
            JOB_POOL.remove(sysScheduleJob.getId());
        }, delayMs, TimeUnit.MILLISECONDS);
    }

    /**
     * 执行系统-定时任务表
     *
     * @param sysScheduleJob 系统-定时任务表
     */
    @Override
    public void execute(SysScheduleJob sysScheduleJob) {
        String jobClass = sysScheduleJob.getJobClass();
        String jsonParams = sysScheduleJob.getParams();
        String errorMsg = null;
        JobStatusEnum executeStatus = JobStatusEnum.COMPLETED;
        //反射创建类的对象
        try {
            //找到执行方法 todo 方法重载
            Class<?> clazz = Class.forName(jobClass);
            Method[] methods = clazz.getDeclaredMethods();
            Method method = Arrays.stream(methods).filter(x -> x.getName().equals(sysScheduleJob.getMethod())).findFirst().orElseThrow(() -> new BusinessException("方法" + sysScheduleJob.getName() + "不存在"));

            //解析参数
            Class<?>[] parameterTypes = method.getParameterTypes();
            Object[] params = SysScheduleJob.convertParams(jsonParams, parameterTypes);

            //更新状态
            updateStatus(sysScheduleJob.getId(), JobStatusEnum.RUNNING);

            //判断是否是静态方法
            if (Modifier.isStatic(method.getModifiers())) {
                //执行静态方法
                method.invoke(null, params);
            } else {
                //获取bean
                Object bean = SpringContextUtils.getBean(clazz);
                if (bean == null) {
                    bean = clazz.getDeclaredConstructor().newInstance();
                }
                method.invoke(bean, params);
            }
        } catch (Exception e) {
            log.error("SysScheduleJob任务执行失败", e);
            errorMsg = e.getMessage();
            executeStatus = JobStatusEnum.FAILED;
        }

        //更新状态
        updateChain()
                .set(SysScheduleJob::getStatus, executeStatus)
                .set(SysScheduleJob::getErrorMsg, StringUtils.substring(errorMsg, 0, 500))
                .eq(SysScheduleJob::getId, sysScheduleJob.getId())
                .update();
    }


    /**
     * 更新状态
     *
     * @param id     任务id
     * @param status 状态
     */
    @Override
    public void updateStatus(Serializable id, JobStatusEnum status) {
        updateChain().set(SysScheduleJob::getStatus, status).eq(SysScheduleJob::getId, id).update();
    }
}