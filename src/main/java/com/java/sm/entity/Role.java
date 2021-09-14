package com.java.sm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.java.sm.entity.base.BaseEntity;
import com.java.sm.valid.group.AddGroup;
import com.java.sm.valid.group.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2021-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_role")
public class Role extends BaseEntity {
    @NotBlank(message = "角色名称不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private String roleName;
    private String roleDesc;


}
