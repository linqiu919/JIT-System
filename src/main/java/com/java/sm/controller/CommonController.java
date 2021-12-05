package com.java.sm.controller;

import com.java.sm.common.domin.LoginAdmin;
import com.java.sm.common.http.AxiosResult;
import com.java.sm.common.http.AxiosStatus;
import com.java.sm.components.TokenService;
import com.java.sm.constants.RedisKey;
import com.java.sm.dto.MenuDTO;
import com.java.sm.entity.Admin;
import com.java.sm.entity.Menu;
import com.java.sm.exception.LoginException;
import com.java.sm.service.AdminService;
import com.java.sm.service.MenuService;
import com.java.sm.service.RoleService;
import com.java.sm.transfer.MenuTransfer;
import com.java.sm.utils.TreeUtil;
import com.java.sm.utils.UploadService;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName CommonController.java
 * @DescriPtion 通用
 * @CreateTime 2021年06月26日 17:13:00
 */
@RestController
@RequestMapping("common")
public class CommonController {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AdminService adminService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuTransfer menuTransfer;

    @Autowired
    private TokenService tokenService;

    /**
     * 文件上传
     *
     * 1.限制文件上传格式： jpg,png
     * 2.大小最多不超过200k
     * 3.宽高必须为100*100
     * 4.上传文件必须是图片
     *
     */
    @PostMapping("upload")
     public AxiosResult<String> upload(@RequestPart Part avatar){
        return AxiosResult.success(uploadService.upload(avatar));
    }

    @GetMapping("getCode/{uuid}")
    public AxiosResult<String> getCode(@PathVariable String uuid){
        //指定验证码图片宽高
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(150, 50);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        // 获取运算的公式：3+2=?
        captcha.getArithmeticString();
        //验证码的值
        String text = captcha.text();
        //将uuid标识和验证码内容放入map集合中
        stringRedisTemplate.opsForValue().set(RedisKey.CODE_KEY+uuid,text);
        String s = captcha.toBase64();
        return AxiosResult.success(s);
    }

    @PostMapping("doLogin")
    public AxiosResult<String> doLogin(@RequestBody Map<String,String> map){
        String code = map.get("code");
        String uuid = map.get("uuid");
        String account = map.get("account");
        String password = map.get("password");
        String uuidResult = stringRedisTemplate.opsForValue().get(RedisKey.CODE_KEY+uuid);
        //判断验证码是否正确
        if(StringUtils.isEmpty(uuidResult) || !uuidResult.equalsIgnoreCase(code)){
            //到此处则代表uuid为空或者与生成的uuid不相等，抛出异常
            throw new LoginException(AxiosStatus.CHECK_CODE_ERROR);
        }
        Admin admin = adminService.getAdminAccount(account);
        //判断用户是否存在
        if(admin==null){
            throw new LoginException(AxiosStatus.ADMIN_NOT_FOUND);
        }
        //判断密码
        if(!bCryptPasswordEncoder.matches(password,admin.getAdminPassword())){
            throw new LoginException(AxiosStatus.ADMIN_PWD_ERR);
        }
        //清除redis中的验证码
        stringRedisTemplate.delete(RedisKey.CODE_KEY+uuid);

        //生成token
        String token = tokenService.createTokenAndCacheLoginAdmin(admin);
        return AxiosResult.success(token);
    }


    @GetMapping("getUserInfo")
    public AxiosResult<Map<String,Object>> getUserInfo(HttpServletRequest httpServletRequest){
        LoginAdmin loginAdminCache = tokenService.getLoginAdminCache();
        Admin admin = loginAdminCache.getAdmin();
        Map<String,Object> map = new HashMap<>();
        map.put("admin",admin);
        //获取用户的信息
        //如果登录用户为超级管理员，返回所有权限
         if(admin.getIsAdmin()){
             List<Menu> allMenu = menuService.findAll();
             //将用户权限加入loginAdmin中
             loginAdminCache.setMenus(allMenu);
             List<MenuDTO> menuDTOS = menuTransfer.toDto(allMenu);
             List<MenuDTO> collect = menuDTOS.stream().filter(menuDTO -> !menuDTO.getMenuType().equals(3)).collect(Collectors.toList());
             List<MenuDTO> btnMenus = menuDTOS.stream().filter(menuDTO -> menuDTO.getMenuType().equals(3)).collect(Collectors.toList());
             List<MenuDTO> root = collect.stream().filter(menuDTO -> menuDTO.getParentId().equals(0L)).collect(Collectors.toList());
             TreeUtil.buildTreeData(root,collect);
             map.put("menuList", root);
             map.put("btnList",btnMenus);
             tokenService.setLoginAdminCache(loginAdminCache);
             return AxiosResult.success(map);
         }else{
             //返回用户菜单信息
             List<Long> roleIds =  adminService.getRolesByAdminId(admin.getId());
             //通过角色找权限
             List<MenuDTO> list = roleService.getMenusByIds(roleIds);
             List<Menu> menus = menuTransfer.toEntity(list);
             loginAdminCache.setMenus(menus);
             //过滤按钮级别权限
             List<MenuDTO> collect = list.stream().filter(menuDTO -> !menuDTO.getMenuType().equals(3)).collect(Collectors.toList());
             List<MenuDTO> btnMenus = list.stream().filter(menuDTO -> menuDTO.getMenuType().equals(3)).collect(Collectors.toList());
             List<MenuDTO> root = collect.stream().filter(menuDTO -> menuDTO.getParentId().equals(0L)).collect(Collectors.toList());
             TreeUtil.buildTreeData(root,collect);
             //保存按钮权限与顶级权限
             map.put("menuList", root);
             map.put("btnList", btnMenus);
             tokenService.setLoginAdminCache(loginAdminCache);
             return AxiosResult.success(map);
         }
    }
}
