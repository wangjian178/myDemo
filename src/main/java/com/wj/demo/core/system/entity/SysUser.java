package com.wj.demo.core.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wj.demo.core.system.enums.UserOnLineStatusEnum;
import com.wj.demo.core.system.enums.UserSexEnum;
import com.wj.demo.core.system.enums.UserStatusEnum;
import com.wj.demo.framework.mybatis.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @ClassName SysUser
 * @Description: 用户信息
 * @Author: W.Jian
 * @CreateDate: 2025/4/1 15:36
 * @Version: 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Schema(title = "SysUser", description = "用户信息")
@TableName("SYS_USER")
public class SysUser extends BaseEntity {

    @Schema(description = "用户名")
    @TableField(value = "USERNAME")
    private String username;

    @Schema(description = "密码")
    @TableField(value = "PASSWORD")
    private String password;

    @Schema(description = "昵称")
    @TableField(value = "NICKNAME")
    private String nickname;

    @Schema(description = "头像")
    @TableField(value = "AVATAR")
    private String avatar;

    @Schema(description = "手机号")
    @TableField(value = "PHONE")
    private String phone;

    @Schema(description = "邮箱")
    @TableField(value = "EMAIL")
    private String email;

    @Schema(description = "性别")
    @TableField(value = "SEX")
    private UserSexEnum sex;

    @Schema(description = "身份证")
    @TableField(value = "ID_CARD")
    private String idCard;

    @Schema(description = "生日")
    @TableField(value = "BIRTHDAY")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birthday;

    @Schema(description = "户籍")
    @TableField(value = "NATIVE_PLACE")
    private String nativePlace;

    @Schema(description = "地址")
    @TableField(value = "ADDRESS")
    private String address;

    @Schema(description = "学历")
    @TableField(value = "EDUCATION")
    private String education;

    @Schema(description = "毕业院校")
    @TableField(value = "SCHOOL")
    private String school;

    @Schema(description = "状态 0=正常,1=禁用")
    @TableField(value = "STATUS")
    private UserStatusEnum status;

    @Schema(description = "在线状态 0=正常,1=禁用")
    @TableField(value = "ONLINE_STATUS")
    private UserOnLineStatusEnum onlineStatus;
}
