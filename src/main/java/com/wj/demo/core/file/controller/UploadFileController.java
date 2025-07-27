package com.wj.demo.core.file.controller;

import com.wj.demo.core.file.entity.SysFile;
import com.wj.demo.core.file.service.IUploadFileService;
import com.wj.demo.framework.common.annotation.OperateLog;
import com.wj.demo.framework.common.enums.OperateTypeEnum;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName FileController
 * @Description: 文件管理
 * @Author: W.Jian
 * @CreateDate: 2025/7/27 17:24
 * @Version:
 */
@RequestMapping("/system/file")
@RestController
public class UploadFileController {

    @Resource
    private IUploadFileService uploadFileService;

    @GetMapping("/upload")
    @OperateLog(module = "文件管理", function = "上传文件", operateType = OperateTypeEnum.UPLOAD)
    public SysFile upload(MultipartFile file) {
        return uploadFileService.upload(file);
    }
}
