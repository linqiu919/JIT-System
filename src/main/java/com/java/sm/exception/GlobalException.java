package com.java.sm.exception;

import com.java.sm.common.http.AxiosResult;
import com.java.sm.common.http.AxiosStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName GlobalException.java
 * @DescriPtion 判断是否有按钮权限
 * @CreateTime 2021年06月24日 16:25:00
 */
@RestControllerAdvice
public class GlobalException {
    /**
     * 处理表单校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AxiosResult<String> handlerFormValidException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        AxiosStatus formValidError = AxiosStatus.FORM_VALID_ERROR;
        //表单校验有误
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(!CollectionUtils.isEmpty(fieldErrors)){
            FieldError fieldError = fieldErrors.get(0);
            //获取详细错误信息
            String defaultMessage = fieldError.getDefaultMessage();
            //返回详细错误信息

            formValidError.setMessage(defaultMessage);
            return AxiosResult.error(formValidError,null);
        }
        formValidError.setMessage("表单校验错误");
        return AxiosResult.error(formValidError,null);

    }

    @ExceptionHandler(AdminExistException.class)
    public AxiosResult<Void> handlerFormValidException(AdminExistException e){
        return AxiosResult.error(e.getAxiosStatus());
    }

    @ExceptionHandler(LoginException.class)
    public AxiosResult<Void> handlerLoginException(LoginException e){
        return AxiosResult.error(e.getAxiosStatus());
    }

    @ExceptionHandler(Throwable.class)
    public AxiosResult<Void> handlerThrowable(Throwable e){
        AxiosStatus netError = AxiosStatus.NET_ERROR;
        netError.setMessage(e.getMessage());
        return AxiosResult.error(netError);
    }

    /**
     * 处理自动校验异常
     */
    @ExceptionHandler(FormValidException.class)
    public AxiosResult<Map<String,String>> handlerFormValidException(FormValidException e){
        AxiosStatus axiosStatus =  e.getAxiosStatus();
        return AxiosResult.error(axiosStatus,e.getMap());
    }
}
