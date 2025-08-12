package com.wj.demo.core.file.service.impl;

import com.wj.demo.core.file.config.SystemFileConfig;
import com.wj.demo.core.file.constant.FileConstant;
import com.wj.demo.core.file.entity.SysFile;
import com.wj.demo.core.file.service.ICommonFileService;
import com.wj.demo.core.file.service.ISysFileService;
import com.wj.demo.framework.common.constant.SymbolicConstant;
import com.wj.demo.framework.common.utils.CollectionUtils;
import com.wj.demo.framework.common.utils.DateUtils;
import com.wj.demo.framework.common.utils.ServletUtils;
import com.wj.demo.framework.exception.enums.ResultCodeEnum;
import com.wj.demo.framework.exception.exception.BaseException;
import com.wj.demo.framework.redis.service.RedisClient;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @ClassName FileUploadServiceImpl
 * @Description: 文件上传
 * @Author: W.Jian
 * @CreateDate: 2025/7/27 17:54
 * @Version:
 */
@Slf4j
@Service
public class CommonFileServiceImpl implements ICommonFileService {

    @Resource
    private SystemFileConfig systemFileConfig;

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
        String filePath = systemFileConfig.getUploadPath()
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
     * @param fileId   文件名
     * @param response 响应
     */
    @Override
    public void download(Long fileId, HttpServletResponse response) {

        //  1.查询文件
        SysFile sysFile = sysFileService.getById(fileId);
        if (sysFile == null) {
            throw new BaseException(ResultCodeEnum.FILE_NOT_FOUND_ERROR);
        }

        //  2.读取文件
        try {
            FileInputStream fileInputStream = new FileInputStream(sysFile.getFilePath());
            BufferedInputStream inputStream = new BufferedInputStream(fileInputStream);
            ServletOutputStream outputStream = response.getOutputStream();
            FileCopyUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            log.error("文件下载失败", e);
            throw new RuntimeException(e);
        }

        //todo 3.非关系型数据库记录下载次数、下载记录
    }

    /**
     * 批量下载
     * 压缩文件
     *
     * @param fileIdList 文件ID
     */
    @Override
    public ResponseEntity<StreamingResponseBody> downloadZip(List<Long> fileIdList) {
        //  1.查询文件
        List<SysFile> sysFileList = sysFileService.listByIds(fileIdList);
        if (CollectionUtils.isEmpty(sysFileList)) {
            throw new BaseException(ResultCodeEnum.FILE_NOT_FOUND_ERROR);
        }

        //  2. 创建动态ZIP流响应
        StreamingResponseBody stream = outputStream -> {
            try (ZipOutputStream zipOut = new ZipOutputStream(outputStream)) {

                for (SysFile file : sysFileList) {
                    // 使用文件名而不是完整路径作为ZipEntry名称
                    ZipEntry zipEntry = new ZipEntry(file.getUploadName() != null ? file.getUploadName() : file.getFileName());
                    zipOut.putNextEntry(zipEntry);

                    try (FileInputStream fis = new FileInputStream(file.getFilePath())) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = fis.read(buffer)) > 0) {
                            zipOut.write(buffer, 0, len);
                        }
                    } catch (IOException e) {
                        log.error("读取文件失败: {}", file.getFilePath(), e);
                    }
                    zipOut.closeEntry();
                }

                zipOut.finish();
            } catch (IOException e) {
                log.error("创建ZIP文件失败", e);
                throw new RuntimeException("压缩文件创建失败", e);
            }
        };

        //  3. 返回响应 utf8编码
        String zipName = java.net.URLEncoder.encode(System.currentTimeMillis() + ".zip", StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + zipName)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE + ";charset=UTF-8").contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(stream);

        //todo 3.非关系型数据库记录下载次数、下载记录
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
