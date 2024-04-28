package com.wj.demo.common.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/25 17:41
 */
@Data
@Accessors(chain = true)
public class UserVO {

    private Long id;

    private String username;

    private String password;
}
