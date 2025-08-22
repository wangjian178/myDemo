package com.wj.demo.core.system.mapper;

import com.mybatisflex.core.BaseMapper;
import com.wj.demo.core.system.entity.SysMenu;
import com.wj.demo.core.system.model.vo.SysMenuVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 映射层。
 *
 * @author wj
 * @since 1.0.0
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 查询所有
     *
     * @param sysMenu 查询参数
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
    List<SysMenuVO> listAll(@Param("subSysId") Long subSysId, @Param("userId") Long userId);
}
