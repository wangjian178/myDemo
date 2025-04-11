package com.wj.demo.core.system.controller;

import com.wj.demo.framework.exception.model.Result;
import com.wj.demo.core.system.entity.SysIdentity;
import com.wj.demo.core.system.service.ISysIdentityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author W.Jian
 * @version 1.0
 * @Desc
 * @date 2024/9/26 14:55
 */
@Tag(name = "流水号生成")
@RestController
@RequestMapping("/identity")
public class SysIdentityController {

    @Resource
    private ISysIdentityService sysIdentityService;

    /**
     * 新增或者修改
     */
    @Operation(summary = "新增或者修改")
    @PostMapping("/saveOrUpdate")
    public Result<?> saveOrUpdate(@RequestBody SysIdentity identity) {
        return Result.ofSuccess(sysIdentityService.saveOrUpdateEntity(identity));
    }

    /**
     * 获取下一个流水号
     */
    @Operation(summary = "获取下一个流水号")
    @GetMapping("/getNextNo")
    public Result<String> getNextNo(@RequestParam(value = "code") String code) {
        Result<String> result = Result.ofSuccess();
        result.setData(sysIdentityService.getNextNo(code));
        return result;
    }
}
