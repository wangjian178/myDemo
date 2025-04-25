package com.wj.demo.framework.baseContext;


import com.wj.demo.framework.common.model.LoginUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Locale;
import java.util.TimeZone;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/17 10:49
 */
@ToString
@Getter
@Setter
@Accessors(chain = true)
public class BaseContext {

    private Long threadId;

    private Locale locale;

    private TimeZone timeZone;

    private String token;

    private LoginUser loginUser;

    /**
     * 私有化构造方法
     */
    private BaseContext() {
    }

    public static BaseContext build() {
        BaseContext baseContext = new BaseContext();
        baseContext.setThreadId(Thread.currentThread().threadId());
        return baseContext;
    }

}
