package com.java.sm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.java.sm.common.annotation.NumberChoose;
import com.java.sm.entity.base.BaseEntity;
import com.java.sm.valid.group.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//指定表名
@TableName(value = "t_menu")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu extends BaseEntity {

    @NotBlank(message = "权限名称不能为空",groups = {MenuGroup.class, DirectoryGroup.class})
    private String menuTitle;
    @NotNull(message = "父ID不能为空",groups = {MenuGroup.class, DirectoryGroup.class, BtnGroup.class})
    private Long parentId;
    @NotNull(message = "权限类型不能为空",groups = {MenuGroup.class, DirectoryGroup.class, BtnGroup.class})
//    @NumberChoose(message = "只能取值1 2 3",values = {1,2,3},groups = {MenuGroup.class,DirectoryGroup.class, BtnGroup.class})
    private Integer menuType;
    @NotNull(message = "排序结果不能为空",groups = {MenuGroup.class, DirectoryGroup.class, BtnGroup.class})
    private Integer sort;
    @NotBlank(message = "路由地址不能为空",groups = {MenuGroup.class,DirectoryGroup.class})
    private String menuRouter;
    @NotBlank(message = "菜单图标不能为空",groups = {MenuGroup.class, DirectoryGroup.class})
    private String menuIcon="";
    @NotBlank(message = "组件地址不能为空",groups = {MenuGroup.class,})
    private String componentPath;
    @NotBlank(message = "组件名称不能为空",groups = {MenuGroup.class,})
    private String componentName;
    @NotBlank(message = "权限标识不能为空",groups = {MenuGroup.class, BtnGroup.class})
    private String permSign;

}
