package com.wj.demo.core.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wj.demo.core.system.entity.SysUser;
import com.wj.demo.core.system.mapper.SysUserMapper;
import com.wj.demo.core.system.service.SysUserService;
import com.wj.demo.framework.mybatis.entity.BaseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
            throw new RuntimeException("用户名已存在");
        }
        //todo 默认角色

        //todo 组织/部门

        //todo 岗位

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
    public int edit(SysUser sysUser) {
        return baseMapper.updateById(sysUser);
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
}




