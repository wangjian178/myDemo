package com.wj.demo.core.system.service;


import com.mybatisflex.core.service.IService;
import com.wj.demo.core.system.entity.SysMenu;
import com.wj.demo.core.system.model.vo.SysMenuVO;

import java.util.List;

/**
 * 服务层。
 *
 * @author wj
 * @since 1.0.0
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 查询菜单
     *
     * @param sysMenu 条件
     * @return 菜单列表
     */
    List<SysMenu> selectList(SysMenu sysMenu);

    /**
     * 查询所有有权限的菜单
     *
     * @param subSysId 子系统ID
     * @param userId   用户ID
     * @return 菜单列表
     */
    List<SysMenuVO> listAll(Long subSysId, Long userId);
}