package com.java.sm.transfer.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName BaseTransfer.java
 * @DescriPtion TODO
 * @CreateTime 2021年06月23日 20:48:00
 */
@Slf4j
public class BaseTransfer<DTO,Entity> {
    /**
     * entity 转 Dto
     */
    public DTO toDto(Entity entity){
        try {
            Class<DTO> entityClass = (Class<DTO>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            DTO dto = entityClass.newInstance();
            BeanUtils.copyProperties(entity,dto);
            return dto;
        } catch (Exception e) {
            log.error("实体类转换DTO错误",e.getMessage());
            return null;
        }

    }

    /**
     * DTO转Entity
     */

    public Entity toEntity(DTO dto){

        try {
            Class<Entity> entityClass = (Class<Entity>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
            Entity entity = entityClass.newInstance();
            BeanUtils.copyProperties(dto,entity);
            return entity;
        } catch (Exception e) {
            log.error("DTO转换实体类失败",e.getMessage());
            return null;
        }
    }



    public List<DTO> toDto(List<Entity> list){
        List<DTO> dtos = new ArrayList<>();
        list.forEach(entity -> dtos.add(toDto(entity)));
        return dtos;
    }


    public List<Entity> toEntity(List<DTO> list){
        List<Entity> entities = new ArrayList<>();
        list.forEach(dto -> entities.add(toEntity(dto)));
        return entities;
    }
}
