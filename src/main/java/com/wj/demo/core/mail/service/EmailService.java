package com.wj.demo.core.mail.service;

import com.wj.demo.core.mail.model.vo.EmailVO;

/**
 * @InterfaceName EmailService
 * @Description
 * @Author W.Jian
 * @Date 2021/5/8 10:37
 **/
public interface EmailService {
    //简单邮件测试
    void sendSimple(EmailVO emailVo);

    //复杂邮件测试
    void sendComplicated(EmailVO emailVo);

    void sendHtml(EmailVO emailVo);
}
