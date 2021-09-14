package com.java.sm.service;

import com.java.sm.entity.ScheduleBean;
import com.java.sm.service.base.BaseService;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName ScheduleBeanService.java
 * @CreateTime 2021年07月02日 15:21:00
 */
public interface ScheduleBeanService extends BaseService<ScheduleBean> {

    int addScheduled(ScheduleBean scheduleBean);

    int updateScheduled(ScheduleBean scheduleBean);
}
