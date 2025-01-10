package com.wj.demo.core.mail.controller;


import com.wj.demo.core.mail.model.vo.EmailVO;
import com.wj.demo.core.mail.service.EmailService;
import com.wj.demo.framework.exception.model.Result;
import jakarta.annotation.Resource;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("email")
public class EmailController {

    @Resource
    private EmailService emailService;

    @PostMapping("sendEmail")
    public Result<?> sendEmail(@RequestBody EmailVO emailVO) {
        List<File> fileList = emailVO.getFileList();
        if (CollectionUtils.isEmpty(fileList)) {
            emailService.sendSimple(emailVO);
        } else {
            emailService.sendComplicated(emailVO);
        }
        return Result.ofSuccess();
    }

    @PostMapping("sendEmails")
    public Result<?> sendEmails(@RequestBody EmailVO emailVO,
                             @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity) {
        for (int i = 0; i < quantity; i++) {
            emailVO.setSubject(emailVO.getSubject() + i);
            emailVO.setText(emailVO.getText() + i);
            List<File> fileList = emailVO.getFileList();
            if (CollectionUtils.isEmpty(fileList)) {
                emailService.sendSimple(emailVO);
            } else {
                emailService.sendComplicated(emailVO);
            }
        }
        return Result.ofSuccess();
    }


}
