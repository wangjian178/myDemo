package com.wj.demo.core.system.service;


import com.mybatisflex.core.service.IService;
import com.wj.demo.core.system.entity.SysOperateLog;

import java.util.List;

/**
 * 系统操作日志 服务层。
 *
 * @author wj
 * @since 1.0.0
 */
public interface ISysOperateLogService extends IService<SysOperateLog> {

    /**
     * 查询系统操作日志
     * @param sysOperateLog 系统操作日志
     * @return 系统操作日志
     */
    List<SysOperateLog> list(SysOperateLog sysOperateLog);
}