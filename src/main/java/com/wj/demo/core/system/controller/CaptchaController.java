package com.wj.demo.core.system.controller;

import com.wj.demo.core.system.model.vo.CaptchaVO;
import com.wj.demo.framework.common.utils.CaptchaUtils;
import com.wj.demo.framework.exception.model.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName CaptchaController
 * @Description: 验证码
 * @Author: W.Jian
 * @CreateDate: 2025/7/18 10:13
 * @Version:
 */
@RequestMapping("/captcha")
@RestController
public class CaptchaController {

    /**
     * 获取验证码
     */
    @RequestMapping("/get")
    public Result<CaptchaVO> get() {
        return Result.ofSuccess(CaptchaUtils.create());
    }
}
