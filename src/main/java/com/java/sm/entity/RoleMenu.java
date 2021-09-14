package com.java.sm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName RoleMenu.java
 * @DescriPtion TODO
 * @CreateTime 2021年07月01日 16:26:00
 */
@Data
@TableName("t_role_menu")
@AllArgsConstructor
public class RoleMenu implements Serializable {
    private Long roleId;
    private Long menuId;

}
