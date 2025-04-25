package com.wj.demo.framework.common.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wj.demo.core.system.enums.UserOnLineStatusEnum;
import com.wj.demo.core.system.enums.UserStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/25 17:41
 */
@Data
@Accessors(chain = true)
@Schema(description = "用户")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoginUser implements UserDetails, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @JSONField(serialize = false)
    @Schema(description = "密码")
    private String password;

    @Schema(description = "状态 0=正常,1=禁用")
    private UserStatusEnum status;

    @Schema(description = "状态 0下线 1在线")
    private UserOnLineStatusEnum onLineStatus;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
