package com.wj.demo.core.file.service;

import com.wj.demo.core.file.entity.SysFile;
import com.wj.demo.framework.exception.model.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;

/**
 * @ClassName IFileUploadService
 * @Description: 文件上传
 * @Author: W.Jian
 * @CreateDate: 2025/7/27 17:50
 * @Version:
 */
public interface ICommonFileService {

    /**
     * 单个文件上传
     *
     * @param file 文件
     * @return 文件
     */
    Result<SysFile> upload(MultipartFile file);

    /**
     * 批量上传
     *
     * @param files 批量文件上传
     * @return 文件
     */
    Result<List<SysFile>> uploadBatch(MultipartFile[] files);

    /**
     * 下载
     *
     * @param fileId   文件ID
     * @param response 响应
     */
    void download(Long fileId, HttpServletResponse response);

    /**
     * 批量下载
     * 压缩文件
     *
     * @param fileIdList 文件ID
     */
    ResponseEntity<StreamingResponseBody> downloadZip(List<Long> fileIdList);

    /**
     * 删除
     *
     * @param fileId 文件Id
     */
    Result<String> remove(Long fileId);

    /**
     * 批量删除
     *
     * @param fileIds 文件名
     */
    Result<String> removeBatch(List<Long> fileIds);

    /**
     * 预览图片
     *
     * @param fileId   文件Id
     * @param response 响应
     */
    void previewImage(Long fileId, HttpServletResponse response);
}
