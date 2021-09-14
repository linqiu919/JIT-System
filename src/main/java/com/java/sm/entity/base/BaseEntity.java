package com.java.sm.entity.base;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.java.sm.valid.group.AddGroup;
import com.java.sm.valid.group.UpdateGroup;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName BaseEntity.java
 * @CreateTime 2021年06月23日 19:34:00
 */
@Data
public class BaseEntity implements Serializable {
    @ExcelProperty(value = "员工id",index = 0)
    @TableId(type = IdType.AUTO)
    @Null(message = "添加时id必须为空",groups = {AddGroup.class})
    @NotNull(message = "修改时id不能为空",groups = {UpdateGroup.class})
    private Long id;
    @ExcelIgnore
    @JsonIgnore
    private Long createBy;
    @ExcelIgnore
    @JsonIgnore
    private LocalDateTime createTime;
    @ExcelIgnore
    @JsonIgnore
    private Long updateBy;
    @ExcelIgnore
    @JsonIgnore
    private LocalDateTime updateTime;

    //添加增加人员修改人员id与时间
    public void setData() {
        if (id == null) {
            this.createBy = 1L;
            this.createTime = LocalDateTime.now();
        } else {
            this.updateBy = 2L;
            this.updateTime = LocalDateTime.now();
        }
    }
}
