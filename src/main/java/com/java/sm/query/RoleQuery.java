package com.java.sm.query;

import com.java.sm.query.base.BaseQuery;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName RoleQuery.java
 * @DescriPtion TODO
 * @CreateTime 2021年06月26日 22:21:00
 */
@Data
public class RoleQuery extends BaseQuery {
    private String roleName;
}
