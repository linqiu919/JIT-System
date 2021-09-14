package com.java.sm.common.scheduled;

import com.java.sm.common.email.EmailService;
import com.java.sm.entity.Admin;
import com.java.sm.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName DemoSchedu.java
 * @DescriPtion 定时发送邮件
 * @CreateTime 2021年07月01日 23:38:00
 */
//@Component
public class DemoSchedul {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private EmailService emailService;


    @Scheduled(cron = "0 4 15 * * *")
    public void test(){
        List<Admin> admins = adminMapper.selectList(null);
        admins.forEach(admin -> {
            //使用异步线程池发送邮件
          emailService.sendMail(admin);
        });

    }
}
