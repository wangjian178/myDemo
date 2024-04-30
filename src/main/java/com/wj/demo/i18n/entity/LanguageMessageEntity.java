package com.wj.demo.i18n.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wj.demo.mybatis.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @author wj
 * @TableName t_language
 */
@TableName(value ="t_language_message")
@Data
public class LanguageMessageEntity extends BaseEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 语言
     */
    private String language;

    /**
     * 键
     */
    private String code;

    /**
     * 值
     */
    private String label;
}