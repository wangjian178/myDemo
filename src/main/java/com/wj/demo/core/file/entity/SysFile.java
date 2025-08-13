package com.wj.demo.core.file.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.wj.demo.framework.mybatisFlex.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文件管理 实体类。
 *
 * @author wj
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data
@Schema(name = "文件管理")
@Table(value = "sys_file")
public class SysFile extends BaseEntity {

    /**
     * 文件名称
     */
    @Schema(description = "文件名称")
    @Column(value = "FILE_NAME")
    private String fileName;

    /**
     * 文件路径
     */
    @Schema(description = "文件路径")
    @Column(value = "FILE_PATH")
    private String filePath;

    /**
     * 文件类型
     */
    @Schema(description = "文件类型")
    @Column(value = "FILE_TYPE")
    private String fileType;

    /**
     * 文件大小
     */
    @Schema(description = "文件大小")
    @Column(value = "FILE_SIZE")
    private Long fileSize;

    /**
     * 上传文件名称
     */
    @Schema(description = "上传文件名称")
    @Column(value = "ORIGIN_FILE_NAME")
    private String originFileName;

    /**
     * 服务器地址
     */
    @Schema(description = "服务器地址")
    @Column(value = "ADDRESS")
    private String address;

    /**
     * 是否目录
     */
    @Schema(description = "是否目录")
    @Column(value = "IS_DIRECTORY")
    private Boolean isDirectory;
}
