package com.java.sm.perm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName HasPerm.javaf
 * @author 722A-08-CXB
 * @version 1.0.0
 * @CreateTime 2021年07月04日 16:09:00
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HasPerm {
    String permSign() default "";
}