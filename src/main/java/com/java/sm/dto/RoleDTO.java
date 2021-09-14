package com.java.sm.dto;

import com.java.sm.dto.base.BaseDTO;
import lombok.Data;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName RoleDTO.java
 * @DescriPtion TODO
 * @CreateTime 2021年06月26日 22:18:00
 */
@Data
public class RoleDTO extends BaseDTO{

    private String roleName;
    private String roleDesc;
}
