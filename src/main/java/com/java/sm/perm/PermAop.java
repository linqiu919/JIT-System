package com.java.sm.perm;

import com.java.sm.common.domin.LoginAdmin;
import com.java.sm.common.http.AxiosStatus;
import com.java.sm.components.TokenService;
import com.java.sm.entity.Menu;
import com.java.sm.exception.LoginException;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName PermAop.java
 * @CreateTime 2021年07月04日 16:30:00
 */
@Aspect
@Component
public class PermAop {

    @Autowired
    private TokenService tokenService;

    @Before("pointCut()")
    public void checkBtnPerm(JoinPoint joinpoint){
        MethodSignature signature = (MethodSignature) joinpoint.getSignature();
        Method method = signature.getMethod();
        HasPerm annotation = method.getAnnotation(HasPerm.class);
        if(annotation!=null){
            String s = annotation.permSign();
            //和数据库中的权限标识进行对比，如有员工权限标有值，证明有权限，如有没有值，则没有权限
            List<Menu> menus = tokenService.getLoginAdminCache().getMenus();
            boolean b = menus.stream().anyMatch(menu -> s.equalsIgnoreCase(menu.getPermSign()));
            if(!b){
                throw new LoginException(AxiosStatus.NO_PERM);
            }

        }

    }

    @Pointcut("execution(* com.java.sm.controller.*.*(..))")
    public void pointCut(){

    }

}
