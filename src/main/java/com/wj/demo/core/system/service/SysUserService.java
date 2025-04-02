package com.wj.demo.core.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wj.demo.core.system.entity.SysUser;
import com.wj.demo.core.system.model.vo.SysUserPasswordVO;
import com.wj.demo.core.system.model.vo.SysUserVO;

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
     * @return 是否成功
     */
    boolean sync(List<SysUser> userList);

    /**
     * 编辑
     *
     * @param sysUser 用户
     * @return 是否成功
     */
    boolean edit(SysUser sysUser);

    /**
     * 删除
     *
     * @param idList id列表
     * @return 数量
     */
    int delete(List<Long> idList);

    /**
     * 查询
     *
     * @param sysUserVO 用户参数
     * @return 用户列表
     */
    List<SysUserVO> list(SysUserVO sysUserVO);

    /**
     * 修改密码
     * @param sysUserPasswordVO 用户
     * @return 是否成功
     */
    boolean updatePassword(SysUserPasswordVO sysUserPasswordVO);
}
