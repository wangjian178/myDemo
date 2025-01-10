package com.wj.demo.core.mail.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @author W.Jian
 */
@Data
@Schema(description = "邮件")
public class EmailVO {
    //标题
    @Schema(description = "标题")
    private String subject;
    //正文
    private String text;
    //
    private String replyTo;
    //收件人
    private String[] to;
    //发件人
    private String from;
    //发送时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sentDate;
    //抄送
    private String[] cc;
    //密送
    private String[] bcc;
    //文件列表
    private List<File> fileList;

}
