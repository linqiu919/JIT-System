package com.java.sm.common.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName NumberChoose.java
 * @DescriPtion 从多个取值中获取一个值
 * @CreateTime 2021年06月25日 22:37:00
 */
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = {NumberChooseHandler.class})
public @interface NumberChoose {
    String message() default "{javax.validation.constraints.NotNull.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int[] values() default {};
}
