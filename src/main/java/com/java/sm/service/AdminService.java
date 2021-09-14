package com.java.sm.service;

import com.java.sm.common.pagebean.PageBean;
import com.java.sm.dto.AdminDTO;
import com.java.sm.dto.BrandDTO;
import com.java.sm.entity.Admin;
import com.java.sm.entity.Brand;
import com.java.sm.query.AdminQuery;
import com.java.sm.query.BrandQuery;
import com.java.sm.service.base.BaseService;

import java.util.List;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName BrandService.java
 * @DescriPtion TODO
 * @CreateTime 2021年06月22日 13:00:00
 */
public interface AdminService extends BaseService<Admin> {

    PageBean<AdminDTO> searchPage(AdminQuery adminQuery);

    boolean hasAdminWithAccountPhoneEmail(Admin admin);

    int addAdminAndAdminRole(Admin admin);

    AdminDTO finAdminAndRolesByAdminId(long id);

    int updateAdminAndRole(Admin admin);

    int deleteAdminAndRole(long id);

    int batchDeleteAdminAndRole(List<Long> ids);

    Admin getAdminAccount(String account);

    List<Long> getRolesByAdminId(Long id);
}
