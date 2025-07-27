package com.wj.demo.core.file.service.impl;

import com.wj.demo.core.file.config.UploadFileConfig;
import com.wj.demo.core.file.constant.FileConstant;
import com.wj.demo.core.file.entity.SysFile;
import com.wj.demo.core.file.service.ISysFileService;
import com.wj.demo.core.file.service.IUploadFileService;
import com.wj.demo.framework.common.constant.SymbolicConstant;
import com.wj.demo.framework.common.utils.DateUtils;
import com.wj.demo.framework.common.utils.ServletUtils;
import com.wj.demo.framework.exception.exception.BaseException;
import com.wj.demo.framework.redis.service.RedisClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @ClassName FileUploadServiceImpl
 * @Description: 文件上传
 * @Author: W.Jian
 * @CreateDate: 2025/7/27 17:54
 * @Version:
 */
@Slf4j
@Service
public class UploadFileServiceImpl implements IUploadFileService {

    @Resource
    private UploadFileConfig uploadFileConfig;

    @Resource
    private RedisClient redisClient;

    @Resource
    private ISysFileService sysFileService;

    /**
     * 生成文件名称
     *
     * @return 文件名称
     */
    private String generateFileName() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern(DateUtils.YYYYMMDD));
        String key = FileConstant.FILE_UPLOAD_KEY + date;
        Integer no = redisClient.get(key);
        if (no == null) {
            no = BigDecimal.ZERO.intValue();
        }
        no++;
        redisClient.set(key, no);
        return String.format("%s%0" + FileConstant.FILE_UPLOAD_SERIAL_NUMBER_LENGTH + "d", date, no);
    }

    /**
     * 创建保存文件
     *
     * @param file 文件
     * @return 文件信息
     */
    private SysFile buildSysFile(MultipartFile file) {
        if (file == null) {
            throw new BaseException("文件不能为空");
        }
        //上传文件名称
        String uploadFileName = file.getOriginalFilename();
        assert uploadFileName != null;
        //文件类型
        String fileType = uploadFileName.substring(uploadFileName.lastIndexOf(SymbolicConstant.DOT)).toLowerCase();
        //生成的文件路径
        String fileName = generateFileName() + fileType;
        //文件保存路径
        String filePath = uploadFileConfig.getUploadPath()
                + File.separator
                + LocalDate.now().format(DateTimeFormatter.ofPattern(DateUtils.YYYY))
                + File.separator
                + LocalDate.now().format(DateTimeFormatter.ofPattern(DateUtils.MM))
                + File.separator
                + LocalDate.now().format(DateTimeFormatter.ofPattern(DateUtils.DD))
                + File.separator
                + fileName;
        //文件大小
        Long fileSize = file.getSize();
        //获取上传的ip地址
        String address = ServletUtils.getRequest().getRemoteAddr();

        //判断是否是文件夹 todo

        return new SysFile()
                .setFileName(fileName)
                .setFilePath(filePath)
                .setFileType(fileType)
                .setFileSize(fileSize)
                .setUploadName(uploadFileName)
                .setAddress(address)
                .setIsDirectory(Boolean.FALSE);
    }

    /**
     * 单个文件上传
     *
     * @param file 文件
     * @return 文件
     */
    @Override
    public SysFile upload(MultipartFile file) {
        //创建文件
        SysFile sysFile = buildSysFile(file);
        File dir = new File(sysFile.getFilePath().substring(0, sysFile.getFilePath().lastIndexOf(File.separator)));
        File uploadFile = new File(sysFile.getFilePath());
        //保存文件
        try {
            if (!dir.exists()) {
                boolean create = dir.mkdirs();
                log.info("目标文件夹不存在，正在创建。。。{} {}", dir.getAbsolutePath(), create);
            }
            if (uploadFile.exists()) {
                boolean create = uploadFile.createNewFile();
            }
            FileCopyUtils.copy(file.getInputStream(), Files.newOutputStream(uploadFile.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //保存数据库
        sysFileService.save(sysFile);
        return sysFile;
    }

    /**
     * 批量上传
     *
     * @param files 批量文件上传
     * @return 文件
     */
    @Override
    public List<SysFile> uploadFiles(List<MultipartFile> files) {
        return List.of();
    }

    /**
     * 下载
     *
     * @param fileName 文件名
     */
    @Override
    public void download(String fileName) {

    }

    /**
     * 批量下载
     *
     * @param fileNames 文件名
     */
    @Override
    public void downloadFiles(List<String> fileNames) {

    }

    /**
     * 预览
     *
     * @param fileName 文件名
     */
    @Override
    public void preview(String fileName) {

    }

    /**
     * 删除
     *
     * @param fileName 文件名
     */
    @Override
    public void delete(String fileName) {

    }

    /**
     * 批量删除
     *
     * @param fileNames 文件名
     */
    @Override
    public void deleteFiles(List<String> fileNames) {

    }
}
