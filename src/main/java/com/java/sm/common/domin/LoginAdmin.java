package com.java.sm.common.domin;

import com.java.sm.entity.Admin;
import com.java.sm.entity.Menu;
import lombok.Data;

import java.util.List;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName LoginAdmin.java
 * @DescriPtion 登录用户
 * @CreateTime 2021年07月04日 19:43:00
 */
@Data
public class LoginAdmin {

    private String uuid;
    //登录用户信息
    private Admin admin;
    //登录用户权限
    private List<Menu> menus;

}
