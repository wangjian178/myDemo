package com.wj.demo.core.file.controller;

import com.mybatisflex.core.paginate.Page;
import com.wj.demo.core.file.entity.SysFile;
import com.wj.demo.core.file.service.ISysFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 文件管理 控制层。
 *
 * @author wj
 * @since 1.0.0
 */
@RestController
@RequestMapping("/sysFile")
@Tag(name = "文件管理控制层")
public class SysFileController {

    @Resource
    private ISysFileService sysFileService;

    /**
     * 添加 文件管理
     *
     * @param sysFile 文件管理
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/save")
    @Operation(summary = "添加文件管理")
    public boolean save(@RequestBody SysFile sysFile) {
        return sysFileService.save(sysFile);
    }


    /**
     * 根据主键删除文件管理
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/remove/{id}")
    @Operation(summary = "根据主键删除文件管理")
    public boolean remove(@PathVariable Serializable id) {
        return sysFileService.removeById(id);
    }


    /**
     * 根据主键更新文件管理
     *
     * @param sysFile 文件管理
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    @Operation(summary = "根据主键更新文件管理")
    public boolean update(@RequestBody SysFile sysFile) {
        return sysFileService.updateById(sysFile);
    }


    /**
     * 查询所有文件管理
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    @Operation(summary = "查询所有文件管理")
    public List<SysFile> list() {
        return sysFileService.list();
    }


    /**
     * 根据文件管理主键获取详细信息。
     *
     * @param id sysFile主键
     * @return 文件管理详情
     */
    @GetMapping("/getInfo/{id}")
    @Operation(summary = "根据文件管理主键获取详细信息")
    public SysFile getInfo(@PathVariable Serializable id) {
        return sysFileService.getById(id);
    }


    /**
     * 分页查询文件管理
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询文件管理")
    public Page<SysFile> page(Page<SysFile> page) {
        return sysFileService.page(page);
    }
}