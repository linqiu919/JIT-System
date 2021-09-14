package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName ProjectApplication.java
 * @CreateTime 2021年06月22日 12:52:00
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"com.java.sm.mapper"})
@EnableScheduling //开启调度
@EnableAsync //开启异步任务，启动线程池，默认有8个线程
@EnableAspectJAutoProxy
public class ProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}


