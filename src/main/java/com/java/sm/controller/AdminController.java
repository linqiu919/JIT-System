package com.java.sm.controller;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.java.sm.common.http.AxiosResult;
import com.java.sm.common.http.AxiosStatus;
import com.java.sm.common.pagebean.PageBean;
import com.java.sm.controller.base.BaseController;
import com.java.sm.dto.AdminDTO;
import com.java.sm.entity.Admin;
import com.java.sm.exception.AdminExistException;
import com.java.sm.perm.HasPerm;
import com.java.sm.query.AdminQuery;
import com.java.sm.service.AdminService;
import com.java.sm.valid.group.AddGroup;
import com.java.sm.valid.group.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName AdminContreller.java
 * @DescriPtion 员工管理
 * @CreateTime 2021年06月22日 13:13:00
 */
@RestController
@RequestMapping("admin")
public class AdminController extends BaseController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    /**
     * 条件分页查询
     */
    @GetMapping("searchPage")
    public AxiosResult<PageBean<AdminDTO>> searchPage(AdminQuery adminQuery){
        PageHelper.startPage(adminQuery.getCurrentPage(),adminQuery.getPageSize());
        PageBean<AdminDTO> pageBean = adminService.searchPage(adminQuery);
        return AxiosResult.success(pageBean);
    }

    /**
     * 通过id查询员工
     */
    @GetMapping("{id}")
    public AxiosResult<AdminDTO> findById(@PathVariable long id){
       AdminDTO adminDTO =  adminService.finAdminAndRolesByAdminId(id);
        return AxiosResult.success(adminDTO);
    }

    /**
     * 添加员工
     */
    @PostMapping("add")
    @HasPerm(permSign = "system:admin:add")
    //@Validated 开启表单校验
    public AxiosResult<Void> add(@Validated(AddGroup.class) @RequestBody Admin admin){
        //添加之前判断员工账号，员工手机号，员工邮箱是否存在
      boolean b =  adminService.hasAdminWithAccountPhoneEmail(admin);
      if(b){
          //员工已经存在
          throw  new AdminExistException(AxiosStatus.ADMIN_EXIST);
      }
          //员工不存在
        //对默认添加的密码进行加密
        admin.setAdminPassword(bCryptPasswordEncoder.encode("123456"));
        //添加员工时同时添加角色
        int row = adminService.addAdminAndAdminRole(admin);
        return toAxios(row);
    }

    /**
     * 修改员工
     */
    @PostMapping("alter")
    @HasPerm(permSign = "system:admin:update")
    public AxiosResult<Void> alter(@Validated(UpdateGroup.class) @RequestBody Admin admin){
        int row = adminService.updateAdminAndRole(admin);
        return toAxios(row);
    }

    /**
     * 删除员工
     */
    @DeleteMapping("{id}")
    @HasPerm(permSign = "system:admin:delete")
    public AxiosResult<Void> add(@PathVariable long id){
        int row = adminService.deleteAdminAndRole(id);
        return toAxios(row);
    }

    /**
     * 批量删除员工
     */
    @DeleteMapping("batch/{ids}")
    @HasPerm(permSign = "system:admin:batchdelete")
    public AxiosResult<Void> add(@PathVariable List<Long> ids){
        return toAxios(adminService.batchDeleteAdminAndRole(ids));
    }

    /**
     * 导出表格
     */
    @GetMapping("exportExcel")
    public AxiosResult<byte[]> exportExcel(HttpServletResponse response) throws Exception{
        List<Admin> list = adminService.findAll();
        //给员工性别赋值
        list.forEach(admin -> {
            admin.setSex(admin.getGender()==0?"男":admin.getGender()==1?"女":"保密");
            try {
                admin.setUrl(new URL(admin.getAdminAvatar()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        EasyExcel.write(byteArrayOutputStream, Admin.class).sheet("信息").doWrite(list);
        return AxiosResult.success(byteArrayOutputStream.toByteArray());
    }



}
