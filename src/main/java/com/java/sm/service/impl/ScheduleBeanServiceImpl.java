package com.java.sm.service.impl;

import com.java.sm.entity.ScheduleBean;
import com.java.sm.mapper.ScheduleBeanMapper;
import com.java.sm.service.ScheduleBeanService;
import com.java.sm.service.base.impl.BaseServiceImpl;
import javafx.concurrent.ScheduledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName ScheduleBeanServiceImpl.java
 * @DescriPtion TODO
 * @CreateTime 2021年07月02日 15:23:00
 */
@Service
@Transactional
public class ScheduleBeanServiceImpl extends BaseServiceImpl<ScheduleBean> implements ScheduleBeanService {


    @Autowired
    private ScheduleBeanMapper scheduleBeanMapper;

    @Override
    public int addScheduled(ScheduleBean scheduleBean) {
        return scheduleBeanMapper.insert(scheduleBean);
    }

    @Override
    public int updateScheduled(ScheduleBean scheduleBean) {
        return scheduleBeanMapper.updateById(scheduleBean);
    }
}
