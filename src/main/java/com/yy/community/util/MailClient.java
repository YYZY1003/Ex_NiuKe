package com.yy.community.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import sun.net.www.MimeEntry;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/*
    发送邮箱类
 */
@Component
public class MailClient {
    private static final Logger logger= LoggerFactory.getLogger(MailClient.class);


    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String form;

    //发送邮箱
    public void sendMail(String to,String subject,String content){
        System.getProperties().setProperty("mail.mime.splitlongparameters", "false");
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            //构造发送邮箱
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            //发件人
            helper.setFrom(form);
            helper.setTo(to);//发去哪
            helper.setSubject(subject);//主题
            helper.setText(content,true);

            //发送
            mailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            logger.error("发送失败");
        }
    }
}
