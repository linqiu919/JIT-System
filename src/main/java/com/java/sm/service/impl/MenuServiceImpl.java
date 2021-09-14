package com.java.sm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.java.sm.common.pagebean.PageBean;
import com.java.sm.dto.MenuDTO;
import com.java.sm.entity.Menu;
import com.java.sm.mapper.MenuMapper;
import com.java.sm.query.MenuQuery;
import com.java.sm.service.MenuService;
import com.java.sm.service.base.impl.BaseServiceImpl;
import com.java.sm.transfer.MenuTransfer;
import com.java.sm.utils.TreeUtil;
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
 * @ClassName MenuServiceImpl.java
 * @CreateTime 2021年06月22日 13:01:00
 */
@Service
@Transactional
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private MenuTransfer menuTransfer;

    @Override
    public PageBean<MenuDTO> searchPage(MenuQuery menuQuery) {
        if(menuQuery.isQuery()){
            //是查询
            //获取所有数据
            LambdaQueryWrapper<Menu> lambda = new QueryWrapper<Menu>().lambda();
            if(!StringUtils.isEmpty(menuQuery.getMenuTitle()))
                lambda.like(Menu::getMenuTitle,menuQuery.getMenuTitle());
            if(Objects.nonNull(menuQuery.getMenuType()))
                lambda.like(Menu::getMenuType,menuQuery.getMenuType());
            if(Objects.nonNull(menuQuery.getStartTime()) && Objects.nonNull(menuQuery.getEndTime()))
                lambda.between(Menu::getCreateTime,menuQuery.getStartTime(),menuQuery.getEndTime());

            List<Menu> menus = menuMapper.selectList(lambda);
            PageInfo<Menu> pageInfo = new PageInfo<>(menus);
            List<MenuDTO> menuDTOS = menuTransfer.toDto(menus);
            return PageBean.init(pageInfo.getTotal(),menuDTOS);


        }else{
            //不是查询
            //查询所有一级分类
            List<Menu> rootMenus = menuMapper.selectList(new QueryWrapper<Menu>().lambda().eq(Menu::getParentId, 0L));
            PageInfo<Menu> pageInfo  = new PageInfo<>(rootMenus);
            List<MenuDTO> rootDtos = menuTransfer.toDto(rootMenus);
            List<Menu> otherMenus = menuMapper.selectList(new QueryWrapper<Menu>().lambda().ne(Menu::getParentId, 0L));
            List<MenuDTO> otherDTOS = menuTransfer.toDto(otherMenus);
//            rootDtos.forEach(menuDTO -> getChildren(menuDTO,otherDTOS));
            TreeUtil.buildTreeData(rootDtos,otherDTOS);
            return  PageBean.init(pageInfo.getTotal(),rootDtos);

        }

//        List<Menu> menus = menuMapper.selectList(lambda);
//        PageInfo<Menu> pageInfo = new PageInfo<>(menus);
//        List<MenuDTO> menuDTOS = menuTransfer.toDto(menus);
//        return  PageBean.init(pageInfo.getTotal(),menuDTOS);

    }

    @Override
    public List<MenuDTO> getTreeData() {
        List<Menu> rootMenus = menuMapper.selectList(new QueryWrapper<Menu>().lambda().eq(Menu::getParentId, 0L));
//        PageInfo<Menu> pageInfo  = new PageInfo<>(rootMenus);
        List<MenuDTO> rootDtos = menuTransfer.toDto(rootMenus);
        List<Menu> otherMenus = menuMapper.selectList(new QueryWrapper<Menu>().lambda().ne(Menu::getParentId, 0L));
        List<MenuDTO> otherDTOS = menuTransfer.toDto(otherMenus);
//        rootDtos.forEach(menuDTO -> getChildren(menuDTO,otherDTOS));
        TreeUtil.buildTreeData(rootDtos,otherDTOS);
        return rootDtos;
    }

    @Override
    public int cascadeDeleteChilde(long id) {
        //删除孩子
//        List<Menu> menus = menuMapper.selectList(new QueryWrapper<Menu>().lambda().eq(Menu::getParentId, id));
//        List<Long> collect = menus.stream().map(Menu::getId).collect(Collectors.toList());

        List<Long> ids = new ArrayList<>();
        getCascadeChildrenIds(id,ids);
        if(ids.size()>0){
            menuMapper.deleteBatchIds(ids);
        }
        return this.delete(id);
    }



//    /**
//     * 通过id获取所有子孙
//     */
//    public void getCascadeChildrenId(Long id,List<Long> ids){
//        List<Menu> menus = menuMapper.selectList(new QueryWrapper<Menu>().lambda().eq(Menu::getParentId, id));
//        List<Long> childrenIds = menus.stream().map(Menu::getId).collect(Collectors.toList());
//
//        if(!CollectionUtils.isEmpty(childrenIds)){
//            ids.addAll(childrenIds);
//            childrenIds.forEach(childrenId->getCascadeChildrenId(childrenId,ids));
//        }
//
//    }


//
//    public void getChildren(MenuDTO menu,List<MenuDTO> list){
//        List<MenuDTO> collect = list.stream().filter(menu1 -> menu1.getParentId().longValue() == menu.getId()).collect(Collectors.toList());
//        if(!CollectionUtils.isEmpty(collect)){
//            menu.setChildren(collect);
//            collect.forEach(menu2 -> getChildren(menu2,list));
//        }
//    }
}
