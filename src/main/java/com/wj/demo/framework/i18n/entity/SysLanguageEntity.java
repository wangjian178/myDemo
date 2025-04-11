package com.wj.demo.framework.i18n.entity;


import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.wj.demo.framework.mybatisFlex.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 
 * @author wj
 * @TableName SYS_LANGUAGE
 */
@Data
@Accessors(chain = true)
@Table(value ="SYS_LANGUAGE")
@Schema(title = "SysLanguageEntity", description = "多语言")
public class SysLanguageEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文本代码
     */
    @Column(value = "CODE")
    @Schema(description = "文本代码")
    private String code;

    /**
     * 语言
     */
    @Column(value = "LANGUAGE")
    @Schema(description = "语言")
    private String language;

    /**
     * 显示内容
     */
    @Column(value = "LABEL")
    @Schema(description = "显示内容")
    private String label;
}