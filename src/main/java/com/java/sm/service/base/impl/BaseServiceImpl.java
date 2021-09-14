package com.java.sm.service.base.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.sm.mapper.base.MyBaseMapper;
import com.java.sm.service.base.BaseService;
import com.java.sm.utils.ReflectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName BaseServiceImpl.java
 * @DescriPtion TODO
 * @CreateTime 2021年06月22日 13:28:00
 */
public class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    private MyBaseMapper<T> myBaseMapper;

    @Override
    public List<T> findAll() {
        return myBaseMapper.selectList(null);
    }

    @Override
    public T findById(Long id) {
        return myBaseMapper.selectById(id);
    }

//    @Override
//    public List<T> findByPage(LambdaQueryWrapper<T> queryWrapper) {
////        queryWrapper.orderByDesc(T::get)
//        return myBaseMapper.selectList(queryWrapper);
//    }

    @Override
    public int update(T t) {
        ReflectionUtil.invokeMethod(t,"setData",null,null);
        return myBaseMapper.updateById(t);
    }

    @Override
    public int insert(T t) {
        ReflectionUtil.invokeMethod(t,"setData",null,null);
        return myBaseMapper.insert(t);
    }

    @Override
    public int delete(Long id) {

        return myBaseMapper.deleteById(id);
    }

    @Transactional
    @Override
    public int batchDelete(List<Long> ids) {
        return myBaseMapper.deleteBatchIds(ids);
    }

    @Override
    public void getCascadeChildrenIds(Long id, List<Long> ids) {
        List<T> ts = myBaseMapper.selectList(new QueryWrapper<T>().eq("parent_id",id));

        if(!CollectionUtils.isEmpty(ts)){
            ts.forEach(t -> {
                ids.add( (Long) ReflectionUtil.getFieldValue(t,"id"));
                getCascadeChildrenIds((Long)ReflectionUtil.getFieldValue(t,"id"),ids);
            });
        }
    }
}
