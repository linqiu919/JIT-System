package com.java.sm.common.email;

import com.java.sm.entity.Admin;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName EmailService.java
 * @DescriPtion 邮件服务
 * @CreateTime 2021年07月01日 23:58:00
 */
@Component
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Async
    public void sendMail(Admin admin){

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");
        try {
            //读取邮件模板
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("salary.ftl");
            Map<String, String> map = new HashMap<>();
            map.put("adminAccount",admin.getAdminAccount());
            map.put("adminName",admin.getAdminName());
            map.put("adminSalary",admin.getAdminSalary()+"");
            map.put("adminPhone",admin.getAdminPhone());
            map.put("adminSalary",admin.getAdminSalary()+"");
            StringWriter stringWriter = new StringWriter();
            template.process(map,stringWriter);
            //邮箱设置
            helper.setFrom("linqiu9911@163.com");
            helper.setTo(admin.getAdminEmail());
            helper.setSubject("工资明细表");
            helper.setText(stringWriter.toString(),true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
