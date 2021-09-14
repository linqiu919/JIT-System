package com.java.sm.config;

import com.java.sm.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName MyWebConfig.java
 * @DescriPtion 登录拦截
 * @CreateTime 2021年07月04日 15:58:00
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/common/upload","/common/getCode/*","/common/doLogin");
    }
}
