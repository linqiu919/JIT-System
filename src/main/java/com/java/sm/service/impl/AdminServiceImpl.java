package com.java.sm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.java.sm.common.pagebean.PageBean;
import com.java.sm.dto.AdminDTO;
import com.java.sm.entity.Admin;
import com.java.sm.entity.AdminRole;
import com.java.sm.entity.Brand;
import com.java.sm.mapper.AdminMapper;
import com.java.sm.mapper.AdminRoleMapper;
import com.java.sm.query.AdminQuery;
import com.java.sm.query.BrandQuery;
import com.java.sm.service.AdminService;
import com.java.sm.service.BrandService;
import com.java.sm.service.base.impl.BaseServiceImpl;
import com.java.sm.transfer.AdminTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName BrandServiceImpl.java
 * @DescriPtion TODO
 * @CreateTime 2021年06月22日 13:01:00
 */
@Service
@Transactional
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminTransfer adminTransfer;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    /**
     * 分页条件查询
     */
    @Override
    public PageBean<AdminDTO> searchPage(AdminQuery adminQuery) {
        LambdaQueryWrapper<Admin> lambda = new QueryWrapper<Admin>().lambda();
        //员工姓名查询条件不为空
        if (!StringUtils.isEmpty(adminQuery.getAdminName()))
            lambda.like(Admin::getAdminName, adminQuery.getAdminName());
        //员工手机号查询条件不为空
        if (!StringUtils.isEmpty(adminQuery.getAdminPhone()))
            lambda.eq(Admin::getAdminPhone, adminQuery.getAdminPhone());
        //员工性别不为null
        if (Objects.nonNull(adminQuery.getGender()))
            lambda.eq(Admin::getGender, adminQuery.getGender());
        //数据添加时间条件不为null
        if (Objects.nonNull(adminQuery.getStartTime()) && Objects.nonNull(adminQuery.getEndTime()))
            lambda.between(Admin::getCreateTime, adminQuery.getStartTime(), adminQuery.getEndTime());
        List<Admin> admins = adminMapper.selectList(lambda);
        PageInfo<Admin> pageInfo = new PageInfo<>(admins);
        List<AdminDTO> adminDTOS = adminTransfer.toDto(admins);
        return PageBean.init(pageInfo.getTotal(), adminDTOS);
    }

    @Override
    public boolean hasAdminWithAccountPhoneEmail(Admin admin) {
        Integer integer = adminMapper.selectCount(new QueryWrapper<Admin>()
                .lambda()
                .or().eq(Admin::getAdminAccount, admin.getAdminAccount())
                .or().eq(Admin::getAdminPhone, admin.getAdminPhone())
                .or().eq(Admin::getAdminEmail, admin.getAdminEmail())
                .or().eq(Admin::getAdminCode, admin.getAdminCode()));
        return integer > 0;
    }

    /**
     * 添加员工和角色
     */
    @Override
    public int addAdminAndAdminRole(Admin admin) {

        this.insert(admin);
        if (!CollectionUtils.isEmpty(admin.getRoleIds()))
            admin.getRoleIds().forEach(roleId -> adminRoleMapper.insert(new AdminRole(roleId, admin.getId())));

        return 1;
    }

    /**
     * 查询员工和员工的角色
     */
    @Override
    public AdminDTO finAdminAndRolesByAdminId(long id) {
        Admin admin = this.findById(id);
        AdminDTO adminDTO = adminTransfer.toDto(admin);
        List<AdminRole> adminRoles = adminRoleMapper.selectList(new QueryWrapper<AdminRole>().lambda().eq(AdminRole::getAdminId, id));

        List<Long> roleIds = adminRoles.stream().map(AdminRole::getRoleId).collect(Collectors.toList());
        adminDTO.setRoleIds(roleIds);
        return adminDTO;
    }

    /**
     * 修改员工和角色
     */
    @Override
    public int updateAdminAndRole(Admin admin) {
        //删除员工所有的角色信息
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().lambda().eq(AdminRole::getAdminId, admin.getId()));

        //添加员工的信息
        if (!CollectionUtils.isEmpty(admin.getRoleIds()))
            admin.getRoleIds().forEach(roleId -> adminRoleMapper.insert(new AdminRole(roleId, admin.getId())));
        return this.update(admin);
    }

    /**
     * 删除员工时同时删除角色
     */
    @Override
    public int deleteAdminAndRole(long id) {
        //删除员工所有的角色信息
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().lambda().eq(AdminRole::getAdminId, id));
        return this.delete(id);
    }

    /**
     * 批量删除员工时同时删除员工角色
     */
    @Override
    public int batchDeleteAdminAndRole(List<Long> ids) {
        //批量删除员工时将员工角色删除
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().lambda().in(AdminRole::getAdminId, ids));
        return this.batchDelete(ids);
    }

    @Override
    public Admin getAdminAccount(String account) {
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().lambda()
                .eq(Admin::getAdminAccount, account));
        return admin;
    }

    /**
     * 通过员工id获取所有的角色
     */
    @Override
    public List<Long> getRolesByAdminId(Long id) {
        List<AdminRole> adminRoles = adminRoleMapper.selectList(new QueryWrapper<AdminRole>()
                .lambda().eq(AdminRole::getAdminId, id));

        List<Long> collect = adminRoles.stream().map(AdminRole::getRoleId).collect(Collectors.toList());

        return collect;
    }
}
