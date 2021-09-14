package com.java.sm.interceptor;

import com.java.sm.components.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName LoginInterceptor.java
 * @CreateTime 2021年07月04日 15:42:00
 */
@Component
public class LoginInterceptor implements HandlerInterceptor{
    @Autowired
    private TokenService tokenService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断用户是否登录
        return tokenService.checkTokenIsSure(request);
    }
}

