package com.wj.demo.core.file.controller;

import com.wj.demo.core.file.entity.SysFile;
import com.wj.demo.core.file.service.ICommonFileService;
import com.wj.demo.core.system.annotation.OperateLog;
import com.wj.demo.framework.common.annotation.CheckReferer;
import com.wj.demo.framework.common.enums.OperateTypeEnum;
import com.wj.demo.framework.exception.model.Result;
import com.wj.demo.framework.security.annotation.AnonymousAccess;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;

/**
 * @ClassName FileController
 * @Description: 文件管理
 * @Author: W.Jian
 * @CreateDate: 2025/7/27 17:24
 * @Version:
 */
@RequestMapping("/common/file")
@RestController
@Tag(name = "文件管理", description = "文件管理")
public class CommonFileController {

    @Resource
    private ICommonFileService commonFileService;

    @PostMapping("/upload")
    @Operation(summary = "文件管理", description = "上传文件")
    @OperateLog(module = "文件管理", function = "上传文件", operateType = OperateTypeEnum.UPLOAD)
    public Result<SysFile> upload(MultipartFile file) {
        return commonFileService.upload(file);
    }

    @PostMapping("/uploadBatch")
    @Operation(summary = "文件管理", description = "批量上传文件")
    @OperateLog(module = "文件管理", function = "批量上传文件", operateType = OperateTypeEnum.UPLOAD)
    public Result<List<SysFile>> uploadBatch(MultipartFile[] files) {
        return commonFileService.uploadBatch(files);
    }

    @GetMapping("/download")
    @Operation(summary = "文件管理", description = "下载文件")
    @OperateLog(module = "文件管理", function = "下载文件", operateType = OperateTypeEnum.DOWNLOAD)
    public void download(@RequestParam(value = "fileId") Long fileId, HttpServletResponse response) {
        commonFileService.download(fileId, response);
    }

    @PostMapping("/downloadZip")
    @Operation(summary = "文件管理", description = "下载zip文件")
    @OperateLog(module = "文件管理", function = "下载zip文件", operateType = OperateTypeEnum.DOWNLOAD)
    public ResponseEntity<StreamingResponseBody> downloadZip(@RequestBody List<Long> fileIdList) {
        return commonFileService.downloadZip(fileIdList);
    }

    @DeleteMapping("/remove")
    @Operation(summary = "文件管理", description = "删除文件")
    @OperateLog(module = "文件管理", function = "删除文件", operateType = OperateTypeEnum.DELETE)
    public Result<String> remove(@RequestParam(value = "fileId") Long fileId) {
        return commonFileService.remove(fileId);
    }

    @DeleteMapping("/removeBatch")
    @Operation(summary = "文件管理", description = "批量删除文件")
    @OperateLog(module = "文件管理", function = "批量删除文件", operateType = OperateTypeEnum.DELETE)
    public Result<String> removeBatch(@RequestBody List<Long> fileIds) {
        return commonFileService.removeBatch(fileIds);
    }

    @PostMapping("/previewImage")
    @Operation(summary = "文件管理", description = "预览图片")
    @AnonymousAccess
    @CheckReferer
    @OperateLog(module = "文件管理", function = "预览图片", operateType = OperateTypeEnum.OTHER)
    public void previewImage(@RequestParam(value = "fileId") Long fileId, HttpServletResponse response) {
        commonFileService.previewImage(fileId, response);
    }
}
