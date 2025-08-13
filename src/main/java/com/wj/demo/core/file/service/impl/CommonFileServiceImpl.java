package com.wj.demo.core.file.service.impl;

import com.alibaba.fastjson.JSON;
import com.wj.demo.core.file.config.SystemFileConfig;
import com.wj.demo.core.file.constant.FileConstant;
import com.wj.demo.core.file.entity.SysFile;
import com.wj.demo.core.file.service.ICommonFileService;
import com.wj.demo.core.file.service.ISysFileService;
import com.wj.demo.framework.common.constant.SymbolicConstant;
import com.wj.demo.framework.common.enums.ImageTypeEnum;
import com.wj.demo.framework.common.utils.CollectionUtils;
import com.wj.demo.framework.common.utils.DateUtils;
import com.wj.demo.framework.common.utils.ServletUtils;
import com.wj.demo.framework.exception.enums.ResultCodeEnum;
import com.wj.demo.framework.exception.exception.BaseException;
import com.wj.demo.framework.exception.model.Result;
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
import java.util.ArrayList;
import java.util.Arrays;
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
        String originFileName = file.getOriginalFilename();
        assert originFileName != null;
        //文件类型
        String fileType = originFileName.substring(originFileName.lastIndexOf(SymbolicConstant.DOT) + 1).toLowerCase();
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
                .setOriginFileName(originFileName)
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
    public Result<SysFile> upload(MultipartFile file) {
        //上传文件
        MultipartFile[] files = {file};
        Result<List<SysFile>> result = uploadBatch(files);

        return Result.isSuccess(result) ? Result.ofSuccess(result.getData().getFirst()) : Result.ofFail(result.getMsg());
    }

    /**
     * 批量上传
     *
     * @param files 批量文件上传
     * @return 文件
     */
    @Override
    public Result<List<SysFile>> uploadBatch(MultipartFile[] files) {
        //上传文件
        List<SysFile> sysFiles = new ArrayList<>();
        //错误信息
        StringBuilder errorMsg = new StringBuilder();

        for (MultipartFile file : files) {
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
                FileCopyUtils.copy(file.getInputStream(), Files.newOutputStream(uploadFile.toPath()));
            } catch (IOException e) {
                errorMsg.append(file.getOriginalFilename()).append(SymbolicConstant.DOTTED_COMMA);
                log.error("文件保存失败 {}", file.getOriginalFilename(), e);
                continue;
            }
            sysFiles.add(sysFile);
        }

        //保存数据库
        sysFileService.saveBatch(sysFiles);

        if (!errorMsg.isEmpty()) {
            return Result.ofFail(sysFiles, errorMsg + "上传失败！");
        }
        return Result.ofSuccess(sysFiles);
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
                    ZipEntry zipEntry = new ZipEntry(file.getOriginFileName() != null ? file.getOriginFileName() : file.getFileName());
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
     * 删除
     *
     * @param fileId 文件Id
     */
    @Override
    public Result<String> remove(Long fileId) {
        return removeBatch(List.of(fileId));
    }

    /**
     * 批量删除
     *
     * @param fileIds 文件名
     */
    @Override
    public Result<String> removeBatch(List<Long> fileIds) {
        //批量查询文件
        List<SysFile> sysFiles = sysFileService.listByIds(fileIds);

        //校验
        if (sysFiles.size() != fileIds.size()) {
            log.error("删除文件失败 {} {}", fileIds, JSON.toJSONStringWithDateFormat(sysFiles, JSON.DEFFAULT_DATE_FORMAT));
            return Result.ofFail(ResultCodeEnum.FILE_NOT_FOUND_ERROR);
        }

        //批量删除源文件
        for (SysFile sysFile : sysFiles) {
            File file = new File(sysFile.getFilePath());
            if (!file.delete()) {
                log.error("删除文件失败 {}", file.getAbsolutePath());
                return Result.ofFail("删除失败！");
            }
        }

        //批量删除数据库文件
        sysFileService.removeByIds(fileIds);

        return Result.ofSuccess();
    }

    /**
     * 预览图片
     *
     * @param fileId   文件ID
     * @param response 响应
     */
    @Override
    public void previewImage(Long fileId, HttpServletResponse response) {
        // 1. 查询文件信息
        SysFile sysFile = sysFileService.getById(fileId);
        if (sysFile == null) {
            throw new BaseException(ResultCodeEnum.FILE_NOT_FOUND_ERROR);
        }

        // 2. 校验是否为图片类型
        ImageTypeEnum imageType = ImageTypeEnum.getImageTypeEnum(sysFile.getFileType());
        if (Arrays.stream(ImageTypeEnum.values()).noneMatch(x -> x.equals(imageType))) {
            throw new BaseException(ResultCodeEnum.FILE_NOT_IMAGE_ERROR);
        }

        // 3. 设置响应头并输出图片流
        try (FileInputStream fis = new FileInputStream(sysFile.getFilePath());
             BufferedInputStream bis = new BufferedInputStream(fis);
             ServletOutputStream outputStream = response.getOutputStream()) {

            // 根据图片类型设置Content-Type
            String contentType = switch (imageType) {
                case ImageTypeEnum.JPG, ImageTypeEnum.JPEG -> MediaType.IMAGE_JPEG_VALUE;
                case ImageTypeEnum.PNG -> MediaType.IMAGE_PNG_VALUE;
                case ImageTypeEnum.GIF -> MediaType.IMAGE_GIF_VALUE;
                default -> MediaType.APPLICATION_OCTET_STREAM_VALUE;
            };

            response.setContentType(contentType);
            // 缓存一周
            response.setHeader(HttpHeaders.CACHE_CONTROL, "public, max-age=604800");

            // 写入图片数据
            FileCopyUtils.copy(bis, outputStream);
            outputStream.flush();

        } catch (IOException e) {
            log.error("图片预览失败: {}", sysFile.getFilePath(), e);
            throw new BaseException(ResultCodeEnum.IMAGE_LOADING_ERROR);
        }

        // 4. 记录预览次数（可选）todo
        // sysFileService.recordPreview(fileId);
    }

    /**
     * 预览PDF
     *
     * @param fileId   文件Id
     * @param response 响应
     */
    @Override
    public void previewPDF(Long fileId, HttpServletResponse response) {

    }
}
