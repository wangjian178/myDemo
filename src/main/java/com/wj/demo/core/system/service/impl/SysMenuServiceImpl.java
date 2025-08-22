package com.wj.demo.core.system.service.impl;


import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wj.demo.core.system.entity.SysMenu;
import com.wj.demo.core.system.mapper.SysMenuMapper;
import com.wj.demo.core.system.model.vo.SysMenuVO;
import com.wj.demo.core.system.service.ISysMenuService;
import com.wj.demo.framework.common.utils.NodeUtils;
import com.wj.demo.framework.common.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务层实现。
 *
 * @author wj
 * @since 1.0.0
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    /**
     * 查询所有有权限的菜单
     *
     * @param sysMenu 条件
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectList(SysMenu sysMenu) {
        return getMapper().selectList(sysMenu);
    }

    /**
     * 查询所有有权限的菜单
     *
     * @param subSysId 子系统ID
     * @param userId   用户ID
     * @return 菜单列表
     */
    @Override
    public List<SysMenuVO> listAll(Long subSysId, Long userId) {
        List<SysMenuVO> sysMenuVOList = getMapper().listAll(subSysId, userId);
        return NodeUtils.tree(sysMenuVOList, SysMenuVO::getId, SysMenuVO::getParentId, SysMenuVO::getChildren);
    }
}