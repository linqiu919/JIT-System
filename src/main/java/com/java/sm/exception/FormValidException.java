package com.java.sm.exception;

import com.java.sm.common.http.AxiosStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author 722A-08-CXB
 * @CreateTime 2021年06月30日 19:30:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormValidException extends RuntimeException{
    private AxiosStatus axiosStatus;
    private Map<String,String> map;
}
