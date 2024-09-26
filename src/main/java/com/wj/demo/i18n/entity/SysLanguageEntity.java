package com.wj.demo.i18n.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wj.demo.mybatis.entity.BaseEntity;
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
@TableName(value ="SYS_LANGUAGE")
@Schema(title = "SysLanguageEntity", description = "多语言")
public class SysLanguageEntity extends BaseEntity implements Serializable {

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