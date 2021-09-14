package com.java.sm.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.java.sm.common.annotation.NumberChoose;
import com.java.sm.entity.base.BaseEntity;
import com.java.sm.valid.group.AddGroup;
import com.java.sm.valid.group.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.net.URL;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2021-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_admin")
@ContentRowHeight(100)
@HeadRowHeight(30)
@ColumnWidth(25)

public class Admin extends BaseEntity {

    @ExcelProperty(value = "员工账号")
    @NotBlank(message = "员工账户不能为空",groups = {AddGroup.class, UpdateGroup.class})
    @Pattern(regexp = "^[a-zA-Z]\\w{5,8}$",groups = {AddGroup.class, UpdateGroup.class})
    private String adminAccount;

    @ExcelProperty(value = "员工姓名")
    @NotBlank(message = "员工姓名不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private String adminName;


    @NumberChoose (values = {0,1,2})
    private Integer gender;
    @ExcelProperty(value = "员工性别")
    private transient String sex;

    @ExcelProperty(value = "员工手机")
    @NotBlank(message = "员工手机号不能为空",groups = {AddGroup.class, UpdateGroup.class})
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$",groups = {AddGroup.class, UpdateGroup.class})
    private String adminPhone;

    @ExcelProperty(value = "员工邮箱")
    @NotBlank(message = "员工邮箱不能为空",groups = {AddGroup.class, UpdateGroup.class})
    @Email(message = "员工邮箱格式不正确",groups = {AddGroup.class, UpdateGroup.class})
    private String adminEmail;

    @ExcelIgnore
    private String adminAvatar;

    @ExcelProperty(value = "员工头像")
    private transient URL url;
    @ExcelIgnore
    private String adminPassword;
    @ExcelIgnore
    private Boolean isActive;
    @ExcelIgnore
    private Boolean isAdmin;

    @ExcelProperty(value = "员工薪资")
    @NotNull(message = "员工薪资不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private Double adminSalary;

    @ExcelProperty(value = "员工地址")
    @NotBlank(message = "员工地址不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private String adminAddress;

    @ColumnWidth(50)
    @ExcelProperty(value = "员工身份证号")
    @NotBlank(message = "员工身份证号不能为空",groups = {AddGroup.class, UpdateGroup.class})
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$",groups = {AddGroup.class, UpdateGroup.class})
    private String adminCode;

    //添加角色id数组属性，由于数据库中不存在的列，所以添加transient属性
    @ExcelIgnore
    private transient List<Long> roleIds;

}
