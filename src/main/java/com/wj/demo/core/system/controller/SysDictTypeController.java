package com.wj.demo.core.system.controller;

import com.wj.demo.core.system.entity.SysDictType;
import com.wj.demo.core.system.service.ISysDictTypeService;
import com.wj.demo.core.system.annotation.OperateLog;
import com.wj.demo.framework.common.enums.OperateTypeEnum;
import com.wj.demo.framework.pageHelper.annotation.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 系统-字典类型 控制层。
 *
 * @author wj
 * @since 1.0.0
 */
@RestController
@RequestMapping("/system/sysDictType")
@Tag(name = "系统-字典类型控制层")
public class SysDictTypeController {

    @Resource
    private ISysDictTypeService sysDictTypeService;

    /**
     * 添加 系统-字典类型
     *
     * @param sysDictType 系统-字典类型
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/save")
    @Operation(summary = "添加系统-字典类型")
    @OperateLog(module = "系统-字典类型", function = "添加", operateType = OperateTypeEnum.ADD)
    public boolean save(@RequestBody SysDictType sysDictType) {
        return sysDictTypeService.save(sysDictType);
    }


    /**
     * 根据主键删除系统-字典类型
     *
     * @param ids 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @PostMapping("/remove")
    @Operation(summary = "根据主键删除系统-字典类型")
    @OperateLog(module = "系统-字典类型", function = "删除", operateType = OperateTypeEnum.DELETE)
    public void remove(@RequestBody List<Serializable> ids) {
        sysDictTypeService.removeByIdList(ids);
    }


    /**
     * 根据主键更新系统-字典类型
     *
     * @param sysDictType 系统-字典类型
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    @Operation(summary = "根据主键更新系统-字典类型")
    @OperateLog(module = "系统-字典类型", function = "更新", operateType = OperateTypeEnum.UPDATE)
    public boolean update(@RequestBody SysDictType sysDictType) {
        return sysDictTypeService.updateById(sysDictType);
    }


    /**
     * 查询所有系统-字典类型
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    @Operation(summary = "查询所有系统-字典类型")
    @Pagination
    public List<SysDictType> list(SysDictType sysDictType) {
        return sysDictTypeService.list(sysDictType);
    }

    /**
     * 根据系统-字典类型主键获取详细信息。
     *
     * @param id sysDictType主键
     * @return 系统-字典类型详情
     */
    @GetMapping("/getInfo/{id}")
    @Operation(summary = "根据系统-字典类型主键获取详细信息")
    public SysDictType getInfo(@PathVariable Serializable id) {
        return sysDictTypeService.getById(id);
    }
}