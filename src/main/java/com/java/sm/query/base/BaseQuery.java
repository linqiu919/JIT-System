package com.java.sm.query.base;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName BaseQuery.java
 * @DescriPtion 封装条件和分页查询父类
 * @CreateTime 2021年06月23日 19:47:00
 */
@Data
public class BaseQuery {
    private int currentPage = 1;
    private int pageSize = 5;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private LocalDateTime startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private LocalDateTime endTime;
}
