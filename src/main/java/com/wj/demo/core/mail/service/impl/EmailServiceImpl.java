package com.wj.demo.core.mail.service.impl;


import com.wj.demo.core.mail.model.vo.EmailVO;
import com.wj.demo.core.mail.service.EmailService;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @author W.Jian
 */
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {


    @Resource
    private JavaMailSenderImpl javaMailSender;

    /*    */
    /**
     * 用来发送模版邮件
     *//*
    @Autowired
    private TemplateEngine templateEngine;*/

    @Value("${spring.mail.username}")
    private String from;

    //简单邮件测试
    @Override
    public void sendSimple(EmailVO emailVo) {
        SimpleMailMessage message = new SimpleMailMessage();
        emailVo.setSentDate(emailVo.getSentDate() == null ? new Date() : emailVo.getSentDate());
        BeanUtils.copyProperties(emailVo, message);
        message.setFrom(from);
        javaMailSender.send(message);
    }


    //复杂邮件测试
    @Override
    public void sendComplicated(EmailVO emailVo) {
        //创建一个复杂的消息邮件
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //用MimeMessageHelper来包装MimeMessage
        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(emailVo.getSubject());
            mimeMessageHelper.setText(emailVo.getText());
            mimeMessageHelper.setTo(emailVo.getTo());
            mimeMessageHelper.setFrom(emailVo.getFrom());
            mimeMessage.setSentDate(emailVo.getSentDate() == null ? new Date() : emailVo.getSentDate());
            List<File> fileList = emailVo.getFileList();
            for (File file : fileList) {
                mimeMessageHelper.addAttachment(file.getName(), file);
            }
        } catch (MessagingException e) {
            log.error("sendComplicated error : {}", e.toString());
        }
        javaMailSender.send(mimeMessage);

    }

    @Override
    public void sendHtml(EmailVO emailVo) {

        /*Context context = new Context();
        context.setVariable("project", "demo");
        context.setVariable("author", "yimcarson");
        context.setVariable("code", emailVo.getText());
        String emailContent = templateEngine.process("mail", context);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(emailVo.getTo());
            helper.setSubject(emailVo.getSubject());
            helper.setText(emailContent, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);*/
    }

}