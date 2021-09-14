package com.java.sm.controller.base;

import com.java.sm.common.http.AxiosResult;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName BaseController.java
 * @DescriPtion TODO
 * @CreateTime 2021年06月22日 19:21:00
 */
public class BaseController {

    public AxiosResult<Void> toAxios(int row){
        return row > 0 ? AxiosResult.success():AxiosResult.error();
    }
}
