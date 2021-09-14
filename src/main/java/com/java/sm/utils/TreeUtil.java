package com.java.sm.utils;

import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName TreeUtil.java
 * @DescriPtion 递归查询，获取权限树
 * @CreateTime 2021年06月30日 09:49:00
 */
public class TreeUtil {

    //通过集合依次排序找孩子id

    /**
     * 参数一： 最顶级的集合
     * 参数二：所有数据
     * 目的：从所有的数据中，查出最顶级的孩子，递归查询
     * @param root
     * @param allList
     * @param <T>
     */
    public static <T> void buildTreeData(List<T> root, List<T> allList){
        List<T> sortList = root.stream().sorted((t1, t2) -> {
            Integer t1Sort = (Integer) ReflectionUtil.getFieldValue(t1, "sort");
            Integer t2Sort = (Integer) ReflectionUtil.getFieldValue(t2, "sort");
            return t1Sort - t2Sort;
        }).collect(Collectors.toList());
        root.clear();
        root.addAll(sortList);
        root.forEach(t -> getChildren(t,allList));
    }

    private static <T> void getChildren(T t, List<T> allList) {
        List<T> children = allList.stream().filter(t1 -> (
                (Long) ReflectionUtil.getFieldValue(t, "id"))
                .equals((Long) ReflectionUtil.getFieldValue(t1, "parentId")
                ))
                .collect(Collectors.toList());

        if(!CollectionUtils.isEmpty(children)){
          List<T>  sortChildren = children.stream().sorted((t1, t2) -> {
                Integer t1Sort = (Integer) ReflectionUtil.getFieldValue(t1, "sort");
                Integer t2Sort = (Integer) ReflectionUtil.getFieldValue(t2, "sort");
                return t1Sort - t2Sort;
            }).collect(Collectors.toList());
            ReflectionUtil.setFieldValue(t,"children",sortChildren);
            children.forEach(t1->getChildren(t1,allList));
        }
    }


}
