package com.wj.demo.core.system.service.impl;


import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wj.demo.core.system.entity.SysOperateLog;
import com.wj.demo.core.system.mapper.SysOperateLogMapper;
import com.wj.demo.core.system.service.ISysOperateLogService;
import com.wj.demo.framework.common.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统操作日志 服务层实现。
 *
 * @author wj
 * @since 1.0.0
 */
@Service
public class ISysOperateLogServiceImpl extends ServiceImpl<SysOperateLogMapper, SysOperateLog> implements ISysOperateLogService {

    /**
     * 查询系统操作日志
     *
     * @param sysOperateLog 系统操作日志
     * @return 系统操作日志
     */
    @Override
    public List<SysOperateLog> list(SysOperateLog sysOperateLog) {
        return queryChain()
                .like(SysOperateLog::getModule, sysOperateLog.getModule(), StringUtils.isNotEmpty(sysOperateLog.getModule()))
                .like(SysOperateLog::getFunction, sysOperateLog.getFunction(), StringUtils.isNotEmpty(sysOperateLog.getFunction()))
                .like(SysOperateLog::getMethod, sysOperateLog.getMethod(), StringUtils.isNotEmpty(sysOperateLog.getMethod()))
                .like(SysOperateLog::getRequestMethod, sysOperateLog.getRequestMethod(), StringUtils.isNotEmpty(sysOperateLog.getRequestMethod()))
                .like(SysOperateLog::getIp, sysOperateLog.getIp(), StringUtils.isNotEmpty(sysOperateLog.getIp()))
                .like(SysOperateLog::getUrl, sysOperateLog.getUrl(), StringUtils.isNotEmpty(sysOperateLog.getUrl()))
                .orderBy(SysOperateLog::getCreateTime).desc()
                .list();
    }
}