package com.java.sm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.java.sm.common.pagebean.PageBean;
import com.java.sm.dto.MenuDTO;
import com.java.sm.dto.RoleDTO;
import com.java.sm.entity.*;
import com.java.sm.entity.Role;
import com.java.sm.mapper.AdminRoleMapper;
import com.java.sm.mapper.MenuMapper;
import com.java.sm.mapper.RoleMapper;
import com.java.sm.mapper.RoleMenuMapper;
import com.java.sm.query.RoleQuery;
import com.java.sm.service.RoleService;
import com.java.sm.service.base.impl.BaseServiceImpl;
import com.java.sm.transfer.MenuTransfer;
import com.java.sm.transfer.RoleTransfer;
import org.apache.poi.ss.formula.functions.IDStarAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName RoleServiceImpl.java
 * @DescriPtion TODO
 * @CreateTime 2021年06月26日 22:26:00
 */
@Service
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleTransfer roleTransfer;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Autowired
    private MenuTransfer menuTransfer;
    @Override
    public PageBean<RoleDTO> searchPage(RoleQuery RoleQuery) {
        LambdaQueryWrapper<Role> lambda = new QueryWrapper<Role>().lambda();
        if(!StringUtils.isEmpty(RoleQuery.getRoleName())){
            lambda.like(Role::getRoleName,RoleQuery.getRoleName());
        }

        if(Objects.nonNull(RoleQuery.getStartTime())&&Objects.nonNull(RoleQuery.getEndTime())){
            lambda.between(Role::getCreateTime,RoleQuery.getStartTime(),RoleQuery.getEndTime());
        }

        List<Role> Roles = roleMapper.selectList(lambda);
        PageInfo<Role> pageInfo = new PageInfo<>(Roles);
        List<RoleDTO> RoleDTOS = roleTransfer.toDto(Roles);
        return PageBean.init(pageInfo.getTotal(),RoleDTOS);
    }

    @Override
    public int setRoleMenu(Long roleId, List<Long> menuIds) {
        //删除已有的权限
        roleMenuMapper.delete(new UpdateWrapper<RoleMenu>().lambda().eq(RoleMenu::getRoleId,roleId));
        //添加权限
        menuIds.forEach(menuId -> roleMenuMapper.insert(new RoleMenu(roleId,menuId)));
        return 1;
    }

    /**
     * 通过roleId获取权限信息
     * @param roleId
     * @return
     */
    @Override
    public List<Long> getMenusByRoleId(Long roleId) {
        //获取权限列表
        List<Long> collect = roleMenuMapper.selectList(new QueryWrapper<RoleMenu>()
                .lambda().eq(RoleMenu::getRoleId, roleId))
                .stream().map(RoleMenu::getMenuId)
                .collect(Collectors.toList());

        //返回在权限列表中没有孩子的
        if(!CollectionUtils.isEmpty(collect)){
            List<Menu> menus = menuMapper.selectBatchIds(collect);
            collect.clear();
            menus.forEach(menu -> {
                if(!menus.stream().anyMatch(item -> item.getParentId().equals(menu.getId()))){
                    //到此处说明没有有孩子，返回
                    collect.add(menu.getId());
                }
            });
        }

        return collect;
    }

    /**
     * 级联删除角色和员工的角色数据
     */
    @Override
    public int deleteCascade(long id) {
        //删除员工和角色中间表中和本角色相关的数据
        adminRoleMapper.delete(new UpdateWrapper<AdminRole>().lambda()
                                .eq(AdminRole::getRoleId,id));

        //删除角色和权限中间表和本角色相关的数据
        roleMenuMapper.delete(new UpdateWrapper<RoleMenu>().lambda()
                                .eq(RoleMenu::getRoleId,id));
        return this.delete(id);
    }

    @Override
    public int batchCascadDelete(List<Long> ids) {
        ids.forEach(id->{
            //删除员工和角色中间表中和本角色相关的数据
            adminRoleMapper.delete(new UpdateWrapper<AdminRole>().lambda()
                    .eq(AdminRole::getRoleId,id));

            //删除角色和权限中间表和本角色相关的数据
            roleMenuMapper.delete(new UpdateWrapper<RoleMenu>().lambda()
                    .eq(RoleMenu::getRoleId,id));
        });

        return this.batchDelete(ids);
    }

    /**
     * 通过roleIds获取所有权限
     */
    @Override
    public List<MenuDTO> getMenusByIds(List<Long> roleIds) {
        if(!CollectionUtils.isEmpty(roleIds)){
            Set<Long> menuIds = roleMenuMapper.selectList(new QueryWrapper<RoleMenu>()
                    .lambda().in(RoleMenu::getRoleId, roleIds))
                    .stream().map(RoleMenu::getMenuId).collect(Collectors.toSet());
            List<Menu> menus = menuMapper.selectList(new QueryWrapper<Menu>().lambda().in(Menu::getId, menuIds));
            List<MenuDTO> menuDTOS = menuTransfer.toDto(menus);
            return menuDTOS;
        }
        return new ArrayList<>();
    }


}
