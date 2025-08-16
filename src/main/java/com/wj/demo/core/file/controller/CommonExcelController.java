package com.wj.demo.core.file.controller;

import com.wj.demo.core.system.annotation.OperateLog;
import com.wj.demo.framework.common.enums.OperateTypeEnum;
import com.wj.demo.framework.common.utils.ExcelUtils;
import com.wj.demo.framework.excel.entity.Excel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName ExcelController
 * @Description: Excel导入导出
 * @Author: W.Jian
 * @CreateDate: 2025/8/16 16:53
 * @Version:
 */
@RestController
@RequestMapping("/common/excel")
@Tag(name = "Excel管理", description = "Excel管理")
public class CommonExcelController {

    @PostMapping("/upload")
    @Operation(summary = "Excel管理", description = "Excel上传")
    @OperateLog(module = "Excel管理", function = "Excel上传", operateType = OperateTypeEnum.UPLOAD)
    public Excel upload(MultipartFile file) {
        return ExcelUtils.read(file);
    }

    @PostMapping("/download")
    @Operation(summary = "Excel管理", description = "下载Excel")
    @OperateLog(module = "Excel管理", function = "下载Excel", operateType = OperateTypeEnum.DOWNLOAD)
    public void download(@RequestBody Excel excel, HttpServletResponse response) {
        ExcelUtils.write(excel, response);
    }
}
