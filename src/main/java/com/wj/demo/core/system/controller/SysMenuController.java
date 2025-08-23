package com.wj.demo.core.system.controller;

import com.wj.demo.core.system.annotation.OperateLog;
import com.wj.demo.core.system.entity.SysMenu;
import com.wj.demo.core.system.model.vo.SysMenuVO;
import com.wj.demo.core.system.service.ISysMenuService;
import com.wj.demo.framework.common.enums.OperateTypeEnum;
import com.wj.demo.framework.pageHelper.annotation.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 系统-菜单管理
 *
 * @author wj
 * @since 1.0.0
 */
@RestController
@RequestMapping("/system/menu")
@Tag(name = "系统-菜单管理")
public class SysMenuController {

    @Resource
    private ISysMenuService sysMenuService;

    /**
     * 添加
     *
     * @param sysMenu 菜单数据
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/save")
    @Operation(summary = "添加")
    @OperateLog(module = "系统-菜单管理", function = "添加", operateType = OperateTypeEnum.ADD)
    public boolean save(@RequestBody SysMenu sysMenu) {
        return sysMenuService.save(sysMenu);
    }

    /**
     * 根据主键更新
     *
     * @param sysMenu 菜单数据
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    @Operation(summary = "根据主键更新")
    @OperateLog(module = "系统-菜单管理", function = "更新", operateType = OperateTypeEnum.UPDATE)
    public boolean update(@RequestBody SysMenu sysMenu) {
        return sysMenuService.update(sysMenu);
    }

    /**
     * 根据主键删除
     *
     * @param idList 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/remove")
    @Operation(summary = "根据主键删除")
    @OperateLog(module = "系统-菜单管理", function = "删除", operateType = OperateTypeEnum.DELETE)
    public boolean remove(@RequestBody List<Long> idList) {
        return sysMenuService.removeByIds(idList);
    }

    /**
     * 查询所有菜单
     *
     * @return 所有数据
     */
    @Pagination
    @GetMapping("/list")
    @Operation(summary = "查询所有菜单")
    public List<SysMenu> selectList(SysMenu sysMenu) {
        return sysMenuService.selectList(sysMenu);
    }

    /**
     * 查询所有有权限的菜单
     *
     * @return 所有数据
     */
    @GetMapping("/listAll")
    @Operation(summary = "查询所有有权限的菜单")
    public List<SysMenuVO> listAll(@RequestParam(required = false, value = "subSysId") Long subSysId) {
        Long userId = null;//SecurityUtils.getUser().getId();
        return sysMenuService.listAll(subSysId, userId);
    }

    /**
     * 根据主键获取详细信息。
     *
     * @param id sysMenu主键
     * @return 详情
     */
    @GetMapping("/getInfo/{id}")
    @Operation(summary = "根据主键获取详细信息")
    public SysMenu getInfo(@PathVariable Serializable id) {
        return sysMenuService.getById(id);
    }
}