package com.wj.demo.i18n.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wj.demo.mybatis.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @author wj
 * @TableName t_language
 */
@TableName(value ="T_LANGUAGE_MESSAGE")
@Data
@Schema(title = "LanguageMessageEntity", description = "多语言")
public class LanguageMessageEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文本代码
     */
    @TableField(value = "CODE")
    @Schema(description = "文本代码")
    private String code;

    /**
     * 语言
     */
    @TableField(value = "LANGUAGE")
    @Schema(description = "语言")
    private String language;

    /**
     * 显示内容
     */
    @TableField(value = "LABEL")
    @Schema(description = "显示内容")
    private String label;
}