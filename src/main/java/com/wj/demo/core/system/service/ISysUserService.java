package com.wj.demo.core.system.service;


import com.mybatisflex.core.service.IService;
import com.wj.demo.core.system.entity.SysUser;
import com.wj.demo.core.system.enums.UserOnLineStatusEnum;
import com.wj.demo.core.system.model.vo.SysUserPasswordVO;
import com.wj.demo.core.system.model.vo.SysUserVO;
import com.wj.demo.framework.common.model.LoginUser;

import java.util.List;

/**
 * @author W.Jian
 * @description 针对表【sys_user(用户表)】的数据库操作Service
 * @createDate 2025-04-02 14:12:40
 */
public interface ISysUserService extends IService<SysUser> {

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
     *
     * @param sysUserPasswordVO 用户
     * @return 是否成功
     */
    boolean updatePassword(SysUserPasswordVO sysUserPasswordVO);

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户
     */
    SysUser queryByUsername(String username);

    /**
     * 根据用户名密码查询用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户
     */
    SysUser queryUserByNameAndPassword(String username, String password);

    /**
     * 更新在线状态
     *
     * @param userId 用户ID
     * @param onlineStatus 在线状态
     */
    void updateOnlineStatus(Long userId, UserOnLineStatusEnum onlineStatus);
}
