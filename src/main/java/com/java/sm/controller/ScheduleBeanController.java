package com.java.sm.controller;

import com.java.sm.common.email.EmailService;
import com.java.sm.common.http.AxiosResult;
import com.java.sm.controller.base.BaseController;
import com.java.sm.dto.ScheduleBeanDTO;
import com.java.sm.entity.Admin;
import com.java.sm.entity.ScheduleBean;
import com.java.sm.mapper.AdminMapper;
import com.java.sm.perm.HasPerm;
import com.java.sm.service.ScheduleBeanService;
import com.java.sm.transfer.ScheduleBeanTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName scheduleBeanContreller.java
 * @DescriPtion 定时控制
 * @CreateTime 2021年06月22日 13:13:00
 */
@RestController
@RequestMapping("schedule")
public class ScheduleBeanController extends BaseController {
    @Autowired
    private ScheduleBeanService scheduleBeanService;

    @Autowired
    private ScheduleBeanTransfer scheduleBeanTransfer;

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private EmailService emailService;

    private Map<Long,ScheduledFuture> map = new HashMap<>();

    /**
     * 查询所有
     */
    @GetMapping()
    public AxiosResult<List<ScheduleBeanDTO>> findAll() {
        return AxiosResult.success(scheduleBeanTransfer.toDto(scheduleBeanService.findAll()));
    }

    /**
     * 通过id查询cron表达式
     */
    @GetMapping("{id}")
    public AxiosResult<ScheduleBeanDTO> findById(@PathVariable long id) {
        ScheduleBean scheduleBean = scheduleBeanService.findById(id);
        ScheduleBeanDTO ScheduleBeanDTO = scheduleBeanTransfer.toDto(scheduleBean);
        return AxiosResult.success(ScheduleBeanDTO);
    }

    /**
     * 添加cron表达式
     */
    @PostMapping("add")
    //@Valid 开启表单校验
    public AxiosResult add(@RequestBody ScheduleBean scheduleBean) {
        return toAxios(scheduleBeanService.addScheduled(scheduleBean));
    }

    /**
     * 修改cron表达式
     */
    @PostMapping("alter")
    @HasPerm(permSign = "task:timer:update")
    public AxiosResult<Void> alter(@RequestBody ScheduleBean scheduleBean) {
        Long cronId = scheduleBean.getCronId();
        ScheduledFuture scheduledFuture = map.get(cronId);
        if(scheduledFuture!=null && !scheduledFuture.isCancelled()){
            scheduledFuture.cancel(false);
        }
        map.remove(cronId);
        int row = scheduleBeanService.updateScheduled(scheduleBean);
      ScheduledFuture scheduledFuture1 =   threadPoolTaskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                List<Admin> admins = adminMapper.selectList(null);
                admins.forEach(admin -> {
                    emailService.sendMail(admin);
                });
            }
        },new CronTrigger(scheduleBean.getCronExpress()));
      map.put(cronId,scheduledFuture1);
        return toAxios(row);
    }

    /**
     * 删除cron表达式
     */
    @DeleteMapping("{id}")
    @HasPerm(permSign = "task:timer:delete")
    public AxiosResult<Void> add(@PathVariable long id) {
        int row = scheduleBeanService.delete(id);
        ScheduledFuture scheduledFuture = map.get(id);
        if(scheduledFuture!=null && !scheduledFuture.isCancelled()){
            scheduledFuture.cancel(true);
        }
        map.remove(id);
        return toAxios(row);
    }

    /**
     * 暂停定时任务
     */
    @PutMapping("{id}")
    @HasPerm(permSign = "task:time:pause")
    public AxiosResult<Void> pause(@PathVariable Long id){
        ScheduledFuture scheduledFuture = map.get(id);
        if(scheduledFuture!=null && !scheduledFuture.isCancelled()){
            //运行时不停止，运行结束停止
            scheduledFuture.cancel(false);
        }
        return AxiosResult.success();
    }

    /**
     * 重启定时任务
     */
    @PostMapping("resume/{id}")
    @HasPerm(permSign = "task:time:resume")
    public AxiosResult<Void> resume(@PathVariable Long id){
        ScheduleBean byId = scheduleBeanService.findById(id);
      ScheduledFuture scheduledFuture =   threadPoolTaskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                List<Admin> admins = adminMapper.selectList(null);
                admins.forEach(admin -> {
                    emailService.sendMail(admin);
                });
            }
        },new CronTrigger(byId.getCronExpress()));
        map.put(byId.getCronId(),scheduledFuture);
         return AxiosResult.success();
    }

    /**
     * 初始化定时任务
     */
    @PostConstruct
    public void initSchedule() {
        List<ScheduleBean> scheduleBeans = scheduleBeanService.findAll();
        scheduleBeans.forEach(scheduleBean -> {
            String cronExpress = scheduleBean.getCronExpress();
            CronTrigger cronTrigger = new CronTrigger(cronExpress);
           ScheduledFuture scheduledFuture =  threadPoolTaskScheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    List<Admin> admins = adminMapper.selectList(null);
                    admins.forEach(admin -> {
                        emailService.sendMail(admin);
                    });
                }
            }, cronTrigger);
           map.put(scheduleBean.getCronId(),scheduledFuture);
        });
    }


}
