package com.wj.demo.core.file.controller;

import com.wj.demo.core.file.entity.SysFile;
import com.wj.demo.core.file.service.ICommonFileService;
import com.wj.demo.framework.common.annotation.OperateLog;
import com.wj.demo.framework.common.enums.OperateTypeEnum;
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

    @GetMapping("/upload")
    @OperateLog(module = "通用文件", function = "上传文件", operateType = OperateTypeEnum.UPLOAD)
    public SysFile upload(MultipartFile file) {
        return commonFileService.upload(file);
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
}
