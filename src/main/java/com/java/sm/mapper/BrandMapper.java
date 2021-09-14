package com.java.sm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.sm.entity.Brand;
import com.java.sm.mapper.base.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName BrandMapper.java
 * @DescriPtion TODO
 * @CreateTime 2021年06月22日 12:56:00
 */
@Mapper
public interface BrandMapper extends MyBaseMapper<Brand> {

}
