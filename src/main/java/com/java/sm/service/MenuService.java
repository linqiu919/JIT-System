package com.java.sm.service;

import com.java.sm.common.pagebean.PageBean;
import com.java.sm.dto.BrandDTO;
import com.java.sm.dto.MenuDTO;
import com.java.sm.entity.Brand;
import com.java.sm.entity.Menu;
import com.java.sm.query.BrandQuery;
import com.java.sm.query.MenuQuery;
import com.java.sm.service.base.BaseService;

import java.util.List;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName BrandService.java
 * @DescriPtion TODO
 * @CreateTime 2021年06月22日 13:00:00
 */
public interface MenuService extends BaseService<Menu> {

    /**
     * 分页条件查询
     */
    PageBean<MenuDTO> searchPage(MenuQuery menuQuery);

    List<MenuDTO> getTreeData();

    int cascadeDeleteChilde(long id);


}
