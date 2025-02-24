package com.wj.demo.core.test.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wj.demo.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author W.Jian
 * @TableName edu_study_record
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "edu_study_record")
@Data
public class EduStudyRecord extends BaseEntity {
    /**
     * 学习日期
     */
    @TableField(value = "STUDY_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate studyDate;

    /**
     * 编码
     */
    @TableField(value = "CODE")
    private String code;

    /**
     * 显示名称
     */
    @TableField(value = "LABEL")
    private String label;

    /**
     * 值
     */
    @TableField(value = "DATA_VALUE")
    private String dataValue;
}