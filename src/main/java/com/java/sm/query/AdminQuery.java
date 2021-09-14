package com.java.sm.query;

import com.java.sm.query.base.BaseQuery;
import lombok.Data;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName BrandQuery.java
 * @DescriPtion 封装查询实体类
 * @CreateTime 2021年06月23日 19:53:00
 */
@Data
public class AdminQuery extends BaseQuery {
    private String adminName;
    private String adminPhone;
    private Integer gender;
}
