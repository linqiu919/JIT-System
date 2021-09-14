package com.java.sm.exception;

import com.java.sm.common.http.AxiosStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName LoginException.java
 * @DescriPtion TODO
 * @CreateTime 2021年07月03日 14:19:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginException extends RuntimeException {
    private AxiosStatus axiosStatus;
}
