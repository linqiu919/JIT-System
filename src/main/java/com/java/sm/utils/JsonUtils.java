package com.java.sm.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName JsonUtils.java
 * @CreateTime 2021年07月04日 20:00:00
 */
public class JsonUtils {
    /**
     * 对象转字符串
     */
    public static String obj2Str(Object obj){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(obj);
            return s;
        }catch (Exception e){
            return "";
        }
    }
    /**
     * 字符串转对象
     */
    public static <T> T str2Object(String jsonStr,Class<T> clazz){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            T t = objectMapper.readValue(jsonStr,clazz);
            return t;
        }catch (Exception e){
            return null;
        }
    }

}
