package com.wj.demo.core.system.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wj.demo.core.system.enums.UserOnLineStatusEnum;
import com.wj.demo.core.system.enums.UserSexEnum;
import com.wj.demo.core.system.enums.UserStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @ClassName SysUserVO
 * @Description: 用户VO
 * @Author: W.Jian
 * @CreateDate: 2025/4/2 14:55
 * @Version:
 */
@Data
@Schema(description = "用户VO")
public class SysUserVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "性别")
    private UserSexEnum sex;

    @Schema(description = "身份证")
    private String idCard;

    @Schema(description = "生日")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birthday;

    @Schema(description = "户籍")
    private String nativePlace;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "学历")
    private String education;

    @Schema(description = "毕业院校")
    private String school;

    @Schema(description = "状态 0=正常,1=禁用")
    private Integer status;

    @Schema(description = "在线状态 0=正常,1=禁用")
    private Integer onlineStatus;

}
