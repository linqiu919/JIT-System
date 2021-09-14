package com.java.sm.controller;

import com.github.pagehelper.PageHelper;
import com.java.sm.common.http.AxiosResult;
import com.java.sm.common.pagebean.PageBean;
import com.java.sm.controller.base.BaseController;
import com.java.sm.dto.MenuDTO;
import com.java.sm.entity.Menu;
import com.java.sm.perm.HasPerm;
import com.java.sm.query.MenuQuery;
import com.java.sm.service.MenuService;
import com.java.sm.transfer.MenuTransfer;
import com.java.sm.utils.FormValidUtils;
import com.java.sm.valid.group.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName MenuContreller.java
 * @DescriPtion 菜单管理
 * @CreateTime 2021年06月22日 13:13:00
 */
@RestController
@RequestMapping("menu")
public class MenuController extends BaseController {
    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuTransfer menuTransfer;
    /**
     * 条件分页查询
     */
    @GetMapping("searchPage")
    public AxiosResult<PageBean<MenuDTO>> searchPage(MenuQuery menuQuery){
//        PageHelper.startPage(menuQuery.getCurrentPage(),menuQuery.getPageSize());
//        List<Menu> all = menuService.findAll();
//        PageInfo<Menu> pageInfo = new PageInfo<>(all);
//        return AxiosResult.success(PageBean.init(pageInfo.getTotal(),all ));
        //*****************************************************

        //开启分页
        PageHelper.startPage(menuQuery.getCurrentPage(),menuQuery.getPageSize());
        PageBean<MenuDTO> pageBean = menuService.searchPage(menuQuery);
        return AxiosResult.success(pageBean);
    }

    /**
     * 通过id查询
     */
    @GetMapping("{id}")
    public AxiosResult<MenuDTO> findById(@PathVariable long id){
        Menu menu = menuService.findById(id);
        MenuDTO menuDTO = menuTransfer.toDto(menu);
        return AxiosResult.success(menuDTO);
    }

    /**
     * 添加权限
     */
    @PostMapping("add")
    @HasPerm(permSign = "system:menu:add")
    //@Valid 开启表单校验
    public AxiosResult add(@Validated(AddGroup.class) @RequestBody Menu menu){
        Integer menuType = menu.getMenuType();
        if(menuType==1){
            //校验目录输入内容
            FormValidUtils.valid(menu, DirectoryGroup.class);
        }
        if(menuType==2){
            //校验菜单输入内容
            FormValidUtils.valid(menu, MenuGroup.class);
        }
        if(menuType==3){
            //校验按钮输入内容
            FormValidUtils.valid(menu, BtnGroup.class);
        }
        return toAxios(menuService.insert(menu));
    }

    /**
     * 修改权限
     */
    @PostMapping("alter")
    @HasPerm(permSign = "system:menu:update")
    public AxiosResult<Void> alter(@Validated(UpdateGroup.class) @RequestBody Menu menu){
        Integer menuType = menu.getMenuType();
        if(menuType==1){
            //校验目录输入内容
            FormValidUtils.valid(menu, DirectoryGroup.class);

        }
        if(menuType==2){
            //校验菜单输入内容
            FormValidUtils.valid(menu, MenuGroup.class);
        }
        if(menuType==3){
            //校验按钮输入内容
            FormValidUtils.valid(menu, BtnGroup.class);
        }
        return toAxios(menuService.update(menu));
    }

    /**
     * 删除权限
     */
    @DeleteMapping("{id}")
    @HasPerm(permSign = "system:menu:delete")
    public AxiosResult<Void> add(@PathVariable long id){

      int row =   menuService.cascadeDeleteChilde(id);
        return toAxios(row);
    }


    /**
     * 获得select框中的选择内容
     */
    @GetMapping("getTreeData")
     public AxiosResult<List<MenuDTO>> getSelectTreeData(){
        List<MenuDTO> list =  menuService.getTreeData();
        return AxiosResult.success(list);
     }


}
