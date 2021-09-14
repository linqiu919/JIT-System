package com.java.sm.dto;

import lombok.Data;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName ScheduleBeanDTO.java
 * @CreateTime 2021年07月02日 15:16:00
 */
@Data
public class ScheduleBeanDTO {
    private Long cronId;
    private String cronExpress;
    private String cronDesc;
}
