package com.wj.demo.core.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wj.demo.core.system.entity.SysUser;
import com.wj.demo.core.system.enums.UserStatusEnum;
import com.wj.demo.core.system.mapper.SysUserMapper;
import com.wj.demo.core.system.model.vo.SysUserPasswordVO;
import com.wj.demo.core.system.model.vo.SysUserVO;
import com.wj.demo.core.system.service.SysUserService;
import com.wj.demo.framework.common.utils.StringUtils;
import com.wj.demo.framework.exception.exception.BaseException;
import com.wj.demo.framework.mybatis.entity.BaseEntity;
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
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

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
        if (lambdaQuery().eq(BaseEntity::getDeleted, Boolean.FALSE).eq(SysUser::getUsername, sysUser.getUsername()).exists()) {
            throw new BaseException("用户名已存在");
        }
        //todo 默认角色

        //todo 组织/部门

        //todo 岗位

        sysUser.setStatus(UserStatusEnum.NORMAL.getCode());
        return baseMapper.insert(sysUser);
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
        return this
                .lambdaUpdate()
                .set(StringUtils.isNotEmpty(sysUser.getUsername()), SysUser::getUsername, sysUser.getUsername())
                .set(StringUtils.isNotEmpty(sysUser.getNickname()), SysUser::getNickname, sysUser.getNickname())
                .set(StringUtils.isNotEmpty(sysUser.getPhone()), SysUser::getPhone, sysUser.getPhone())
                .set(StringUtils.isNotEmpty(sysUser.getEmail()), SysUser::getEmail, sysUser.getEmail())
                .set(Objects.nonNull(sysUser.getSex()), SysUser::getSex, sysUser.getSex())
                .set(StringUtils.isNotEmpty(sysUser.getIdCard()), SysUser::getIdCard, sysUser.getIdCard())
                .set(Objects.nonNull(sysUser.getBirthday()), SysUser::getBirthday, sysUser.getBirthday())
                .set(StringUtils.isNotEmpty(sysUser.getNativePlace()), SysUser::getNativePlace, sysUser.getNativePlace())
                .set(StringUtils.isNotEmpty(sysUser.getAddress()), SysUser::getAddress, sysUser.getAddress())
                .set(StringUtils.isNotEmpty(sysUser.getEducation()), SysUser::getEducation, sysUser.getEducation())
                .set(StringUtils.isNotEmpty(sysUser.getSchool()), SysUser::getSchool, sysUser.getSchool())
                .set(StringUtils.isNotEmpty(sysUser.getRemark()), SysUser::getRemark, sysUser.getRemark())
                .set(Objects.nonNull(sysUser.getStatus()), SysUser::getStatus, sysUser.getStatus())
                .set(Objects.nonNull(sysUser.getOnlineStatus()), SysUser::getOnlineStatus, sysUser.getOnlineStatus())
                .set(StringUtils.isNotEmpty(sysUser.getAvatar()), SysUser::getAvatar, sysUser.getAvatar())
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
        return baseMapper.deleteBatchIds(idList);
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
        return this
                .lambdaUpdate()
                .set(StringUtils.isNotEmpty(sysUserPasswordVO.getNewPassword()), SysUser::getPassword, sysUserPasswordVO.getNewPassword())
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
        return baseMapper.list(sysUserVO);
    }
}




