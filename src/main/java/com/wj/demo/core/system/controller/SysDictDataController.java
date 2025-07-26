package com.wj.demo.core.system.controller;

import com.wj.demo.core.system.entity.SysDictData;
import com.wj.demo.core.system.service.ISysDictDataService;
import com.wj.demo.framework.common.annotation.OperateLog;
import com.wj.demo.framework.common.enums.OperateTypeEnum;
import com.wj.demo.framework.common.model.Node;
import com.wj.demo.framework.pageHelper.annotation.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 系统-字典项 控制层。
 *
 * @author wj
 * @since 1.0.0
 */
@RestController
@RequestMapping("/system/sysDictData")
@Tag(name = "系统-字典项控制层")
public class SysDictDataController {

    @Resource
    private ISysDictDataService sysDictDataService;

    /**
     * 添加 系统-字典项
     *
     * @param sysDictData 系统-字典项
     */
    @PostMapping("/add")
    @Operation(summary = "添加系统-字典项")
    @OperateLog(module = "系统-字典项", function = "添加", operateType = OperateTypeEnum.ADD)
    public void add(@RequestBody SysDictData sysDictData) {
        sysDictDataService.add(sysDictData);
    }

    @PostMapping("/addBatch")
    @Operation(summary = "批量添加系统-字典项")
    @OperateLog(module = "系统-字典项", function = "批量添加", operateType = OperateTypeEnum.ADD)
    public void addBatch(@RequestBody List<SysDictData> sysDictDataList) {
        sysDictDataService.addBatch(sysDictDataList);
    }

    /**
     * 根据主键删除系统-字典项
     *
     * @param ids 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @PostMapping("/remove")
    @Operation(summary = "根据主键删除系统-字典项")
    @OperateLog(module = "系统-字典项", function = "删除", operateType = OperateTypeEnum.DELETE)
    public boolean remove(@RequestBody List<Serializable> ids) {
        return sysDictDataService.removeByIds(ids);
    }


    /**
     * 根据主键更新系统-字典项
     *
     * @param sysDictData 系统-字典项
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    @Operation(summary = "根据主键更新系统-字典项")
    @OperateLog(module = "系统-字典项", function = "更新", operateType = OperateTypeEnum.UPDATE)
    public Boolean update(@RequestBody SysDictData sysDictData) {
        return sysDictDataService.updateById(sysDictData);
    }

    /**
     * 根据系统-字典项主键获取详细信息。
     *
     * @param id sysDictData主键
     * @return 系统-字典项详情
     */
    @GetMapping("/getInfo/{id}")
    @Operation(summary = "根据系统-字典项主键获取详细信息")
    public SysDictData getInfo(@PathVariable Serializable id) {
        return sysDictDataService.getById(id);
    }

    /**
     * 查询所有系统-字典项
     *
     * @return 所有数据
     */
    @GetMapping("/listAll")
    @Operation(summary = "查询所有系统-字典项")
    public List<SysDictData> listAll(SysDictData sysDictData) {
        return sysDictDataService.list(sysDictData);
    }

    /**
     * 查询所有系统-字典项 树状
     *
     * @return 所有数据
     */
    @GetMapping("/listAsTree")
    @Operation(summary = "查询所有系统-字典项 树状")
    public List<Node<SysDictData>> listAsTree(@RequestParam(value = "dictType") String dictType) {
        return sysDictDataService.listAsTree(dictType);
    }

    /**
     * 分页查询所有系统-字典项
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    @Operation(summary = "分页查询所有系统-字典项")
    @Pagination
    public List<SysDictData> list(SysDictData sysDictData) {
        return sysDictDataService.list(sysDictData);
    }
}