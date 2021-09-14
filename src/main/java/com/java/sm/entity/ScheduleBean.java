package com.java.sm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName ScheduleBean.java
 * @DescriPtion TODO
 * @CreateTime 2021年07月02日 15:11:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_schedule")
@AllArgsConstructor
public class ScheduleBean {
    @TableId(type = IdType.AUTO)
    private Long cronId;
    private String cronExpress;
    private String cronDesc;
}
