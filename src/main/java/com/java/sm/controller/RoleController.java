package com.java.sm.controller;

import com.github.pagehelper.PageHelper;
import com.java.sm.common.http.AxiosResult;
import com.java.sm.common.pagebean.PageBean;
import com.java.sm.controller.base.BaseController;
import com.java.sm.dto.RoleDTO;
import com.java.sm.entity.Role;
import com.java.sm.perm.HasPerm;
import com.java.sm.query.RoleQuery;
import com.java.sm.service.RoleService;
import com.java.sm.transfer.RoleTransfer;
import com.java.sm.valid.group.AddGroup;
import com.java.sm.valid.group.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName RoleContreller.java
 * @DescriPtion 角色管理
 * @CreateTime 2021年06月22日 13:13:00
 */
@RestController
@RequestMapping("role")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleTransfer roleTransfer;
    /**
     * 条件分页查询
     */
    @GetMapping("searchPage")
    public AxiosResult<PageBean<RoleDTO>> searchPage(RoleQuery roleQuery){
//        PageHelper.startPage(RoleQuery.getCurrentPage(),RoleQuery.getPageSize());
//        List<Role> all = roleService.findAll();
//        PageInfo<Role> pageInfo = new PageInfo<>(all);
//        return AxiosResult.success(PageBean.init(pageInfo.getTotal(),all ));
        //*****************************************************
        //开启分页
        PageHelper.startPage(roleQuery.getCurrentPage(),roleQuery.getPageSize());
        PageBean<RoleDTO> pageBean = roleService.searchPage(roleQuery);
        return AxiosResult.success(pageBean);
    }

    /**
     * 查询所有
     */
    @GetMapping
    public AxiosResult<List<RoleDTO>> list(){
        return AxiosResult.success(roleTransfer.toDto(roleService.findAll()));
    }
//
    /**
     * 通过id查询角色
     */
    @GetMapping("{id}")
    public AxiosResult<RoleDTO> findById(@PathVariable long id){
        Role Role = roleService.findById(id);
        RoleDTO RoleDTO = roleTransfer.toDto(Role);
        return AxiosResult.success(RoleDTO);
    }

    /**
     * 添加角色
     */
    @PostMapping("add")
    @HasPerm(permSign = "system:role:add")
    //@Valid 开启表单校验
    public AxiosResult add(@Validated(AddGroup.class) @RequestBody Role Role){
        return toAxios(roleService.insert(Role));
    }

    /**
     * 修改角色
     */
    @PostMapping("alter")
    @HasPerm(permSign = "system:role:update")
    public AxiosResult<Void> alter(@Validated(UpdateGroup.class) @RequestBody Role Role){
        return toAxios(roleService.update(Role));
    }

    /**
     * 删除角色
     */
    @DeleteMapping("{id}")
    @HasPerm(permSign = "system:role:delete")
    public AxiosResult<Void> delete(@PathVariable long id){

        return toAxios(roleService.deleteCascade(id));
    }

    /**
     * 批量删除角色
     */
    @DeleteMapping("batch/{ids}")
    @HasPerm(permSign = "system:role:batchdelete")
    public AxiosResult<Void> deleteBatch(@PathVariable List<Long> ids){
        return toAxios(roleService.batchCascadDelete(ids));
    }


    /**
     *设置角色权限
     */
    @PostMapping("{roleId}/menu/{menuIds}")
    public AxiosResult<Void> setRoleMenu(@PathVariable Long roleId,@PathVariable List<Long> menuIds){
        int row = roleService.setRoleMenu(roleId,menuIds);
        return toAxios(row);
    }

    /**
     * 获取角色的权限，在点击角色行时，自动将角色权限展示在树状权限中
     */
    @GetMapping("{roleId}/menus")
    public AxiosResult<List<Long>> getMenusByRoleId(@PathVariable Long roleId){
       List<Long> menuIds =  roleService.getMenusByRoleId(roleId);
       return AxiosResult.success(menuIds);
    }

}
