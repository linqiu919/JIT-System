package com.java.sm.common.annotation;

import org.springframework.util.CollectionUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName NumberChooseHandler.java
 * @CreateTime 2021年06月26日 09:45:00
 */
public class NumberChooseHandler implements ConstraintValidator<NumberChoose,Integer> {

    private List<Integer> list;

    @Override
    public void initialize(NumberChoose constraintAnnotation) {
        int[] values = constraintAnnotation.values();
        list = CollectionUtils.arrayToList(list);

    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return list.contains(value);
    }
}
