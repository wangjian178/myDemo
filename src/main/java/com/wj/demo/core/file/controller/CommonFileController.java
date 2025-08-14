package com.wj.demo.core.file.controller;

import com.wj.demo.core.file.entity.SysFile;
import com.wj.demo.core.file.service.ICommonFileService;
import com.wj.demo.framework.common.annotation.CheckReferer;
import com.wj.demo.core.system.annotation.OperateLog;
import com.wj.demo.framework.common.enums.OperateTypeEnum;
import com.wj.demo.framework.exception.model.Result;
import com.wj.demo.framework.security.annotation.AnonymousAccess;
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
@RequestMapping("/system/file")
@RestController
public class CommonFileController {

    @Resource
    private ICommonFileService commonFileService;

    @PostMapping("/upload")
    @OperateLog(module = "通用文件", function = "上传文件", operateType = OperateTypeEnum.UPLOAD)
    public Result<SysFile> upload(MultipartFile file) {
        return commonFileService.upload(file);
    }

    @PostMapping("/uploadBatch")
    @OperateLog(module = "通用文件", function = "批量上传文件", operateType = OperateTypeEnum.UPLOAD)
    public Result<List<SysFile>> uploadBatch(MultipartFile[] files) {
        return commonFileService.uploadBatch(files);
    }

    @GetMapping("/download")
    @OperateLog(module = "通用文件", function = "下载文件", operateType = OperateTypeEnum.DOWNLOAD)
    public void download(@RequestParam(value = "fileId") Long fileId, HttpServletResponse response) {
        commonFileService.download(fileId, response);
    }

    @PostMapping("/downloadZip")
    @OperateLog(module = "通用文件", function = "下载zip文件", operateType = OperateTypeEnum.DOWNLOAD)
    public ResponseEntity<StreamingResponseBody> downloadZip(@RequestBody List<Long> fileIdList) {
        return commonFileService.downloadZip(fileIdList);
    }

    @DeleteMapping("/remove")
    @OperateLog(module = "通用文件", function = "删除文件", operateType = OperateTypeEnum.DELETE)
    public Result<String> remove(@RequestParam(value = "fileId") Long fileId) {
        return commonFileService.remove(fileId);
    }

    @DeleteMapping("/removeBatch")
    @OperateLog(module = "通用文件", function = "批量删除文件", operateType = OperateTypeEnum.DELETE)
    public Result<String> removeBatch(@RequestBody List<Long> fileIds) {
        return commonFileService.removeBatch(fileIds);
    }

    @PostMapping("/previewImage")
    @AnonymousAccess
    @CheckReferer
    @OperateLog(module = "通用文件", function = "预览图片", operateType = OperateTypeEnum.OTHER)
    public void previewImage(@RequestParam(value = "fileId") Long fileId, HttpServletResponse response) {
        commonFileService.previewImage(fileId, response);
    }
}
