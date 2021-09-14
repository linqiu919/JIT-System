package com.java.sm.dto.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import sun.util.resources.LocaleData;

import java.time.LocalDateTime;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName BaseDto.java
 * @DescriPtion 统一返回前端数据的父类
 * @CreateTime 2021年06月23日 20:41:00
 */
@Data
public class BaseDTO {
    private Long id;

    @JsonIgnore //转json时忽略此数据
    private Long createBy;
    @JsonIgnore //转json时忽略此数据
    private Long updateBy;
    @JsonIgnore //转json时忽略此数据
    private LocalDateTime createTime;
    @JsonIgnore //转json时忽略此数据
    private LocalDateTime updateTime;
}
