package com.java.sm.common.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName AxiosStatus.java
 * @DescriPtion 错误状态码
 * @CreateTime 2021年06月22日 14:32:00
 */
@Getter
@AllArgsConstructor
public enum AxiosStatus {

    Ok(20000,"操作成功"),
    ERROR(40000,"操作失败"),
    FORM_VALID_ERROR(50000,"表单校验失败"),
    ADMIN_EXIST(60000,"员工已经存在"),
    NOT_IMAGE(70000,"不是图片"),
    IMG_TYPE_ERROR(70001,"图片格式不正确"),
    IMG_TOO_LARGE(70002,"图片尺寸 过大"),
    IMG_SIZE_ERR(70003,"图片尺寸错误"),
    IMG_UPLOAD_ERROR(70004,"文件上传失败"),
    NET_ERROR(40004,"服务器错误"),
    CHECK_CODE_ERROR(444888,"验证码为空或输入错误"),
    ADMIN_NOT_FOUND(444777,"没有找到用户"),
    ADMIN_PWD_ERR(444999,"密码错误"),
    NO_LOGIN_ERROR(444449,"未登录"),
    TOKEN_ERROR(444998,"token格式错误"),
    NO_PERM(888999,"没有权限"),
    ;

    private int status;
    private String message;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
