package com.wj.demo.core.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wj.demo.core.system.entity.SysUser;
import com.wj.demo.core.system.model.vo.SysUserVO;

import java.util.List;

/**
 * @author W.Jian
 * @Description: 针对表【sys_user(用户表)】的数据库操作Mapper
 * @Author: W.Jian
 * @Entity com.wj.demo.core.system.entity.SysUser
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 条件查询
     * @param sysUserVO 查询条件
     * @return 用户列表
     */
    List<SysUserVO> list(SysUserVO sysUserVO);
}




