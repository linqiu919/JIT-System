package com.java.sm.query;

import com.java.sm.query.base.BaseQuery;
import lombok.Data;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName BrandQuery.java
 * @DescriPtion TODO
 * @CreateTime 2021年06月23日 19:53:00
 */
@Data
public class BrandQuery extends BaseQuery {
    private String brandName;
    private String brandDesc;
}
