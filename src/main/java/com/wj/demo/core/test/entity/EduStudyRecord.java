package com.wj.demo.core.test.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.wj.demo.framework.mybatisFlex.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author W.Jian
 * @TableName edu_study_record
 */
@EqualsAndHashCode(callSuper = true)
@Table(value = "edu_study_record")
@Data
public class EduStudyRecord extends BaseEntity {
    /**
     * 学习日期
     */
    @Column(value = "STUDY_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate studyDate;

    /**
     * 编码
     */
    @Column(value = "CODE")
    private String code;

    /**
     * 显示名称
     */
    @Column(value = "LABEL")
    private String label;

    /**
     * 值
     */
    @Column(value = "DATA_VALUE")
    private String dataValue;
}