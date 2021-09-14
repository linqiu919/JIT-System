package com.java.sm.common.pagebean;

import lombok.Data;

import java.util.List;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName PageBean.java
 * @DescriPtion 分页包装类
 * @CreateTime 2021年06月22日 19:01:00
 */
@Data
public class PageBean<T> {

    private long total;
    private List<T> data;

    private PageBean(long total,List<T> data){
            this.data = data;
            this.total = total;
    }

    public static <T> PageBean<T> init(long total,List<T> data){
        return new PageBean<T>(total,data );
    }

}
