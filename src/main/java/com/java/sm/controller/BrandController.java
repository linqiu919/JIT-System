package com.java.sm.controller;

import com.github.pagehelper.PageHelper;
import com.java.sm.common.http.AxiosResult;
import com.java.sm.common.pagebean.PageBean;
import com.java.sm.controller.base.BaseController;
import com.java.sm.dto.BrandDTO;
import com.java.sm.entity.Brand;
import com.java.sm.perm.HasPerm;
import com.java.sm.query.BrandQuery;
import com.java.sm.service.BrandService;
import com.java.sm.transfer.BrandTransfer;
import com.java.sm.valid.group.AddGroup;
import com.java.sm.valid.group.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName BrandContreller.java
 * @DescriPtion 品牌管理
 * @CreateTime 2021年06月22日 13:13:00
 */
@RestController
@RequestMapping("brand")
public class BrandController extends BaseController {
    @Autowired
    private BrandService brandService;

    @Autowired
    private BrandTransfer brandTransfer;
    /**
     * 条件分页查询
     */
    @GetMapping("searchPage")
    public AxiosResult<PageBean<BrandDTO>> searchPage(BrandQuery brandQuery){
//        PageHelper.startPage(brandQuery.getCurrentPage(),brandQuery.getPageSize());
//        List<Brand> all = brandService.findAll();
//        PageInfo<Brand> pageInfo = new PageInfo<>(all);
//        return AxiosResult.success(PageBean.init(pageInfo.getTotal(),all ));

        //开启分页
        PageHelper.startPage(brandQuery.getCurrentPage(),brandQuery.getPageSize());
        PageBean<BrandDTO> pageBean = brandService.searchPage(brandQuery);
        return AxiosResult.success(pageBean);
    }

//    /**
//     * 分页查询
//     */
//    @GetMapping("findByPage")
//    public AxiosResult<List<BrandDTO>> findByPage(){
//        return AxiosResult.success(brandService.findAll());
//    }
//
    /**
     * 通过id查询
     */
    @GetMapping("{id}")
    public AxiosResult<BrandDTO> findById(@PathVariable long id){
        Brand brand = brandService.findById(id);
        BrandDTO brandDTO = brandTransfer.toDto(brand);
        return AxiosResult.success(brandDTO);
    }

    /**
     * 添加品牌
     */
    @PostMapping("add")
    @HasPerm(permSign = "goods:brand:add")
    //@Valid 开启表单校验
    public AxiosResult add(@Validated(AddGroup.class) @RequestBody Brand brand){
        return toAxios(brandService.insert(brand));
    }

    /**
     * 修改品牌
     */
    @PostMapping("alter")
    @HasPerm(permSign = "goods:brand:update")
    public AxiosResult<Void> alter(@Validated(UpdateGroup.class) @RequestBody Brand brand){
        return toAxios(brandService.update(brand));
    }

    /**
     * 删除品牌
     */
    @DeleteMapping("{id}")
    @HasPerm(permSign = "goods:brand:delete")
    public AxiosResult<Void> add(@PathVariable long id){
        return toAxios(brandService.delete(id));
    }

    /**
     * 批量删除
     */
    @DeleteMapping("batch/{ids}")
    @HasPerm(permSign = "goods:brand:batchdelete")
    public AxiosResult<Void> add(@PathVariable List<Long> ids){
        return toAxios(brandService.batchDelete(ids));
    }


}
