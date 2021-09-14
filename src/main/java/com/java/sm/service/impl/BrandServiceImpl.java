package com.java.sm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.java.sm.common.pagebean.PageBean;
import com.java.sm.dto.BrandDTO;
import com.java.sm.entity.Brand;
import com.java.sm.mapper.BrandMapper;
import com.java.sm.query.BrandQuery;
import com.java.sm.service.BrandService;
import com.java.sm.service.base.BaseService;
import com.java.sm.service.base.impl.BaseServiceImpl;
import com.java.sm.transfer.BrandTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName BrandServiceImpl.java
 * @DescriPtion TODO
 * @CreateTime 2021年06月22日 13:01:00
 */
@Service
@Transactional
public class BrandServiceImpl extends BaseServiceImpl<Brand> implements BrandService{

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private BrandTransfer brandTransfer;

    @Override
    public PageBean<BrandDTO> searchPage(BrandQuery brandQuery) {
        LambdaQueryWrapper<Brand> lambda = new QueryWrapper<Brand>().lambda();
        if(!StringUtils.isEmpty(brandQuery.getBrandName()))
            lambda.like(Brand::getBrandName,brandQuery.getBrandName());
        if(!StringUtils.isEmpty(brandQuery.getBrandDesc()))
            lambda.like(Brand::getBrandDesc,brandQuery.getBrandDesc());
        //创建时间在勾选的时间之内
        if (Objects.nonNull(brandQuery.getStartTime()) && Objects.nonNull(brandQuery.getEndTime())) {
            lambda.between(Brand::getCreateTime,brandQuery.getStartTime(),brandQuery.getEndTime());

        }

//        List<Brand> brands = super.findByPage(lambda);
        //按照Id进行排序,倒叙
        lambda.orderByDesc(Brand::getId);
        List<Brand> brands = brandMapper.selectList(lambda);
        PageInfo<Brand> brandPageInfo = new PageInfo<>(brands);
        List<BrandDTO> list = new ArrayList<>();
        List<BrandDTO> brandDTOS = brandTransfer.toDto(brands);

        return  PageBean.init(brandPageInfo.getTotal(),brandDTOS);
    }
}
