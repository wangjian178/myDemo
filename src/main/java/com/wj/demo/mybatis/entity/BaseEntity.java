package com.wj.demo.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.wj.demo.mybatis.annotation.CreatedBy;
import com.wj.demo.mybatis.annotation.CreatedTime;
import com.wj.demo.mybatis.annotation.UpdatedBy;
import com.wj.demo.mybatis.annotation.UpdatedTime;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/17 15:20
 */
@Data
@Accessors(chain = true)
public class BaseEntity {

    /**
     * ID
     * AUTO 自增
     * ASSIGN_ID支持自动生成
     * ASSIGN_UUID 字符串UUID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 创建人
     */
    @CreatedBy
    private String createdBy;

    /**
     * 创建时间
     */
    @CreatedTime
    private Date createdTime;

    /**
     * 修改人
     */
    @UpdatedBy
    private String updatedBy;

    /**
     * 修改时间
     */
    @UpdatedTime
    private Date updatedTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标识0否1是
     */
    //@TableLogic
    private Integer delFlag;
}
