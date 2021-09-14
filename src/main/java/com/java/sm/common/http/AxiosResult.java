package com.java.sm.common.http;

import lombok.Data;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName AxiosResult.java
 * @DescriPtion 返回结果
 * @CreateTime 2021年06月22日 14:31:00
 */
@Data
public class AxiosResult<T> {
    private int status;
    private String message;
    private T data;

    private AxiosResult(AxiosStatus axiosStatus,T t){
        this.status = axiosStatus.getStatus();
        this.message = axiosStatus.getMessage();
        this.data = t;
    }

    //获取结果集对象
    private static <T> AxiosResult<T> getInstance(AxiosStatus axiosStatus,T t){
        return new AxiosResult<T>(axiosStatus,t);
    }

    //访问成功不携带数据
    public  static <T> AxiosResult<T> success(){
        return getInstance(AxiosStatus.Ok,null);
    }
    //访问成功携带数据
    public static <T> AxiosResult<T> success(T t){
        return getInstance(AxiosStatus.Ok,t);
    }

    //访问失败不携带数据
    public static <T> AxiosResult<T> error(){
        return getInstance(AxiosStatus.ERROR,null);
    }
    //访问失败携带数据
    public static <T> AxiosResult<T> error(T t){
        return getInstance(AxiosStatus.ERROR,t);
    }

    /*
     自定义失败状态码
    */
    public static <T> AxiosResult<T> error(AxiosStatus axiosStatus) {
        return getInstance(axiosStatus, null);
    }

    //自定义失败状态携带数据
    public static <T> AxiosResult<T> error(AxiosStatus axiosStatus,T t){
        return getInstance(axiosStatus,t);
    }


}
