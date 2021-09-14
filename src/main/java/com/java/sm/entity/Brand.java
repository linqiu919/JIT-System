package com.java.sm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.java.sm.entity.base.BaseEntity;
import com.java.sm.valid.group.AddGroup;
import com.java.sm.valid.group.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName Brand.java
 * @DescriPtion TODO
 * @CreateTime 2021年06月22日 12:52:00
 */
//指定表名
@TableName(value = "t_brand")
@Data

public class Brand extends BaseEntity {
    @NotBlank(message = "品牌名称不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private String brandName;
    @NotBlank(message = "品牌站点不能为空",groups = {AddGroup.class,UpdateGroup.class})
    @URL(message = "品牌站点必须为链接格式",groups = {AddGroup.class,UpdateGroup.class})
    private String brandSite;
    private String brandLogo;
    private String brandDesc;

}
