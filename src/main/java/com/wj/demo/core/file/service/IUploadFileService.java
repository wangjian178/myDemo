package com.wj.demo.core.file.service;

import com.wj.demo.core.file.entity.SysFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName IFileUploadService
 * @Description: 文件上传
 * @Author: W.Jian
 * @CreateDate: 2025/7/27 17:50
 * @Version:
 */
public interface IUploadFileService {

    /**
     * 单个文件上传
     *
     * @param file 文件
     * @return 文件
     */
    SysFile upload(MultipartFile file);

    /**
     * 批量上传
     *
     * @param files 批量文件上传
     * @return 文件
     */
    List<SysFile> uploadFiles(List<MultipartFile> files);

    /**
     * 下载
     *
     * @param fileName 文件名
     */
    void download(String fileName);

    /**
     * 批量下载
     *
     * @param fileNames 文件名
     */
    void downloadFiles(List<String> fileNames);

    /**
     * 预览
     *
     * @param fileName 文件名
     */
    void preview(String fileName);

    /**
     * 删除
     *
     * @param fileName 文件名
     */
    void delete(String fileName);

    /**
     * 批量删除
     *
     * @param fileNames 文件名
     */
    void deleteFiles(List<String> fileNames);
}
