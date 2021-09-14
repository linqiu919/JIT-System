package com.java.sm.dto;

import com.java.sm.dto.base.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName BradnDTO.java
 * @DescriPtion 品牌DTO
 * @CreateTime 2021年06月23日 20:43:00
 */
@Data
public class AdminDTO extends BaseDTO {

        private String adminAccount;
        private String adminName;
        private Integer gender;
        private String adminPhone;
        private String adminEmail;
        private String adminAvatar;
        private Boolean isActive;
        private Boolean isAdmin;
        private Double adminSalary;
        private String adminAddress;
        private String adminCode;
        //添加角色ID属性
        private List<Long> roleIds;
}
