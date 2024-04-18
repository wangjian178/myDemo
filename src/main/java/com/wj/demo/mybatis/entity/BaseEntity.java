package com.wj.demo.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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
     */
    @TableId
    private Integer id;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 修改人
     */
    private String updtedBy;

    /**
     * 修改时间
     */
    private Date updatedDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标识0否1是
     */
    private Integer delFlag;
}
