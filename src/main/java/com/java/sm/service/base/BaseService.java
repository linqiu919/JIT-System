package com.java.sm.service.base;

import com.baomidou.mybatisplus.core.conditions.AbstractLambdaWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.sm.entity.Brand;
import com.java.sm.entity.Menu;

import java.util.List;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName BaseService.java
 * @CreateTime 2021年06月22日 13:25:00
 */
public interface BaseService<T> {
    /**
     * 查询所有
     */
    List<T> findAll();

    /**
     * 通过id查询
     */
    T findById(Long id);
    /**
     * 分页查询
     */
//    List<T> findByPage(LambdaQueryWrapper<T> queryWrapper);

    /**
     * 更新品牌
     */
    int update(T t);

    /**
     * 增加品牌
     */
    int insert(T T);

    /**
     * 删除品牌(根据iD)
     */
    int delete(Long id);

    /**
     * 批量删除
     */
    int batchDelete(List<Long> ids);

    /**
     * 级联获取子id
     */
    void getCascadeChildrenIds(Long id,List<Long> ids);


}
