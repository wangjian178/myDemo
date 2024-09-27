package com.wj.demo.system.controller;

import com.wj.demo.exception.model.Result;
import com.wj.demo.system.entity.SysIdentity;
import com.wj.demo.system.service.SysIdentityService;
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
    private SysIdentityService identityService;

    /**
     * 新增或者修改
     */
    @Operation(summary = "新增或者修改")
    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody SysIdentity identity) {
        identityService.saveOrUpdateEntity(identity);
        return Result.ofSuccess();
    }

    /**
     * 获取下一个流水号
     */
    @Operation(summary = "获取下一个流水号")
    @GetMapping("/getNextNo")
    public Result<String> getNextNo(@RequestParam(value = "code") String code) {
        Result<String> result = Result.ofSuccess();
        result.setData(identityService.getNextNo(code));
        return result;
    }
}
