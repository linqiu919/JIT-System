package com.java.sm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.java.sm.dto.base.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName MenuDTO.java
 * @DescriPtion TODO
 * @CreateTime 2021年06月29日 13:02:00
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuDTO extends BaseDTO {

    private String menuTitle;

    private Long parentId;

    private Integer menuType;

    private Integer sort;

    private String menuRouter;

    private String menuIcon;

    private String componentPath;

    private String componentName;

    private String permSign;

    private List<MenuDTO> children;
}
