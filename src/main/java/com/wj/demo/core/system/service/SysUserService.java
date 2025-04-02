package com.wj.demo.core.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wj.demo.core.system.entity.SysUser;

import java.util.List;

/**
 * @author W.Jian
 * @description 针对表【sys_user(用户表)】的数据库操作Service
 * @createDate 2025-04-02 14:12:40
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 注册
     *
     * @param sysUser 用户
     * @return 数量
     */
    int register(SysUser sysUser);

    /**
     * 同步用户
     *
     * @param userList 用户列表
     * @return 数量
     */
    boolean sync(List<SysUser> userList);

    /**
     * 编辑
     *
     * @param sysUser 用户
     * @return 数量
     */
    int edit(SysUser sysUser);

    /**
     * 删除
     *
     * @param idList id列表
     * @return 数量
     */
    int delete(List<Long> idList);
}
