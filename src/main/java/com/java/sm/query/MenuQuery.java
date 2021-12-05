package com.java.sm.query;

import com.java.sm.query.base.BaseQuery;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName MenuQuery.java
 * @DescriPtion TODO
 * @CreateTime 2021年06月29日 13:06:00
 */
@Data
public class MenuQuery extends BaseQuery {
    private String menuTitle;
    private Integer menuType;

    public boolean isQuery(){
        //判断是否是查询
     return  !(
             StringUtils.isEmpty(menuTitle)
             && Objects.isNull(menuType)
             && Objects.isNull(getStartTime())
             && Objects.isNull(getEndTime())
     );
    }
}
