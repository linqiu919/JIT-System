package com.java.sm.exception;

import com.java.sm.common.http.AxiosResult;
import com.java.sm.common.http.AxiosStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName AdminExitException.java
 * @author 722A-08-CXB
 * @version 1.0.0
 * @DescriPtion 员工不存在的异常
 * @CreateTime 2021年06月26日 16:31:00
 */
@Data
@AllArgsConstructor
public class AdminExistException extends RuntimeException{
    //TODO 分别处理员工信息的四个异常 包括员工已经存在、邮箱已经存在、手机号已经存在、身份证号已经存在
    private AxiosStatus axiosStatus;

}
