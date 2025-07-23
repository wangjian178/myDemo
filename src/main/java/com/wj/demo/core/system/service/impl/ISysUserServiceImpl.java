package com.wj.demo.core.system.service.impl;


import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wj.demo.core.system.entity.SysUser;
import com.wj.demo.core.system.enums.UserOnLineStatusEnum;
import com.wj.demo.core.system.enums.UserStatusEnum;
import com.wj.demo.core.system.mapper.SysUserMapper;
import com.wj.demo.core.system.model.vo.SysUserPasswordVO;
import com.wj.demo.core.system.model.vo.SysUserVO;
import com.wj.demo.core.system.service.ISysUserService;
import com.wj.demo.framework.common.utils.StringUtils;
import com.wj.demo.framework.exception.exception.BaseException;
import com.wj.demo.framework.mybatisFlex.entity.BaseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author W.Jian
 * @Description: 针对表【sys_user(用户表)】的数据库操作Service实现
 * @CreateDate: 2025-04-02 14:12:40
 */
@Service
public class ISysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    /**
     * 注册
     *
     * @param sysUser 用户
     * @return 数量
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int register(SysUser sysUser) {
        //校验用户名
        if (queryChain().eq(BaseEntity::getDeleted, Boolean.FALSE).eq(SysUser::getUsername, sysUser.getUsername()).exists()) {
            throw new BaseException("用户名已存在");
        }
        //todo 默认角色

        //todo 组织/部门

        //todo 岗位

        sysUser.setStatus(UserStatusEnum.NORMAL);
        return mapper.insert(sysUser);
    }

    /**
     * 同步用户
     *
     * @param userList 用户列表
     * @return 数量
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean sync(List<SysUser> userList) {
        return this.saveBatch(userList);
    }

    /**
     * 编辑
     *
     * @param sysUser 用户
     * @return 数量
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean edit(SysUser sysUser) {
        return updateChain()
                .set(SysUser::getUsername, sysUser.getUsername(), StringUtils.isNotEmpty(sysUser.getUsername()))
                .set(SysUser::getNickname, sysUser.getNickname(), StringUtils.isNotEmpty(sysUser.getNickname()))
                .set(SysUser::getPhone, sysUser.getPhone(), StringUtils.isNotEmpty(sysUser.getPhone()))
                .set(SysUser::getEmail, sysUser.getEmail(), StringUtils.isNotEmpty(sysUser.getEmail()))
                .set(SysUser::getSex, sysUser.getSex(), Objects.nonNull(sysUser.getSex()))
                .set(SysUser::getIdCard, sysUser.getIdCard(), StringUtils.isNotEmpty(sysUser.getIdCard()))
                .set(SysUser::getBirthday, sysUser.getBirthday(), Objects.nonNull(sysUser.getBirthday()))
                .set(SysUser::getNativePlace, sysUser.getNativePlace(), StringUtils.isNotEmpty(sysUser.getNativePlace()))
                .set(SysUser::getAddress, sysUser.getAddress(), StringUtils.isNotEmpty(sysUser.getAddress()))
                .set(SysUser::getEducation, sysUser.getEducation(), StringUtils.isNotEmpty(sysUser.getEducation()))
                .set(SysUser::getSchool, sysUser.getSchool(), StringUtils.isNotEmpty(sysUser.getSchool()))
                .set(SysUser::getRemark, sysUser.getRemark(), StringUtils.isNotEmpty(sysUser.getRemark()))
                .set(SysUser::getStatus, sysUser.getStatus(), Objects.nonNull(sysUser.getStatus()))
                .set(SysUser::getOnlineStatus, sysUser.getOnlineStatus(), Objects.nonNull(sysUser.getOnlineStatus()))
                .set(SysUser::getAvatar, sysUser.getAvatar(), StringUtils.isNotEmpty(sysUser.getAvatar()))
                .eq(SysUser::getId, sysUser.getId())
                .update();
    }

    /**
     * 删除
     *
     * @param idList id列表
     * @return 数量
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int delete(List<Long> idList) {
        return mapper.deleteBatchByIds(idList);
    }

    /**
     * 修改密码
     *
     * @param sysUserPasswordVO 用户
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updatePassword(SysUserPasswordVO sysUserPasswordVO) {
        return updateChain()
                .set(SysUser::getPassword, sysUserPasswordVO.getNewPassword(), StringUtils.isNotEmpty(sysUserPasswordVO.getNewPassword()))
                .eq(SysUser::getId, sysUserPasswordVO.getId())
                .update();
    }

    /**
     * 查询
     *
     * @param sysUserVO 用户参数
     * @return 用户列表
     */
    @Override
    public List<SysUserVO> list(SysUserVO sysUserVO) {
        return mapper.list(sysUserVO);
    }

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    @Override
    public SysUser queryByUsername(String username) {
        return queryChain()
                .eq(SysUser::getUsername, username)
                .one();
    }

    /**
     * 根据用户名密码查询用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户
     */
    @Override
    public SysUser queryUserByNameAndPassword(String username, String password) {
        return queryChain()
                .eq(SysUser::getDeleted, Boolean.FALSE)
                .eq(SysUser::getUsername, username)
                .eq(SysUser::getPassword, password)
                .one();
    }

    /**
     * 更新在线状态
     *
     * @param userId 用户ID
     * @param onlineStatus 在线状态
     */
    @Override
    public void updateOnlineStatus(Long userId, UserOnLineStatusEnum onlineStatus) {
        updateChain()
                .set(SysUser::getOnlineStatus, onlineStatus)
                .eq(SysUser::getId, userId)
                .update();
    }
}




