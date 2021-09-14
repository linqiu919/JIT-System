package com.java.sm.service;

import com.java.sm.common.pagebean.PageBean;
import com.java.sm.dto.MenuDTO;
import com.java.sm.dto.RoleDTO;
import com.java.sm.entity.Role;
import com.java.sm.query.RoleQuery;
import com.java.sm.service.base.BaseService;

import java.util.List;
import java.util.Set;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName RoleService.java
 * @DescriPtion TODO
 * @CreateTime 2021年06月26日 22:25:00
 */
public interface RoleService extends BaseService<Role> {

    PageBean<RoleDTO> searchPage(RoleQuery roleQuery);

    int setRoleMenu(Long roleId, List<Long> menuIds);

    List<Long> getMenusByRoleId(Long roleId);

    int deleteCascade(long id);

    int batchCascadDelete(List<Long> ids);

    /**
     * 通过角色id获取所有权限
     */
    List<MenuDTO> getMenusByIds(List<Long> roleIds);
}
