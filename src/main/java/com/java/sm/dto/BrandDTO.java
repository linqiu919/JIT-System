package com.java.sm.dto;

import com.java.sm.dto.base.BaseDTO;
import lombok.Data;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName BradnDTO.java
 * @DescriPtion 品牌DTO
 * @CreateTime 2021年06月23日 20:43:00
 */
@Data
public class BrandDTO extends BaseDTO {
    private String brandName;
    private String brandSite;
    private String brandLogo;
    private String brandDesc;
}
