package com.java.sm.components;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.java.sm.common.domin.LoginAdmin;
import com.java.sm.common.http.AxiosStatus;
import com.java.sm.entity.Admin;
import com.java.sm.exception.LoginException;
import com.java.sm.utils.JsonUtils;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName TokenService.java
 * @DescriPtion TODO
 * @CreateTime 2021年07月04日 14:52:00
 */
@Component
public class TokenService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static final String serret="WDNMD";

    /**
     * 生成token缓存登录用户信息
     */
    public String createTokenAndCacheLoginAdmin(Admin admin){
        String s = UUID.randomUUID().toString();
        LoginAdmin loginAdmin = new LoginAdmin();
        loginAdmin.setAdmin(admin);
        loginAdmin.setUuid(s);
        setLoginAdminCache(loginAdmin);
        return getToken(s);
    }

    /**
     * 将登录用户信息存储到redis中
     * @param loginAdmin
     */
    public void setLoginAdminCache(LoginAdmin loginAdmin){
        stringRedisTemplate.opsForValue().set(loginAdmin.getUuid(), JsonUtils.obj2Str(loginAdmin));

    }

    /**
     * 生成token
     */
    public String getToken(String uuid){
        Algorithm algorithm = Algorithm.HMAC256(serret);
        String token = JWT.create()
                .withIssuer("大哥")
                .withSubject("登录token")
                .withClaim("uuid",uuid)
                .sign(algorithm);
        return token;
    }
    /**
     * 解析token
     */
    public DecodedJWT verifyToken(HttpServletRequest httpServletRequest){
        String authentication = httpServletRequest.getHeader("Authetication");
        String token = authentication.split(" ")[1];
        Algorithm algorithm = Algorithm.HMAC256(serret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("大哥")
                .withSubject("登录token")
                .build(); //Reusable verifier instance
        DecodedJWT jwt = verifier.verify(token);
        return jwt;
    }

    /**
     * 获取token中的uuid
     */
    public String getUUID(){
        DecodedJWT decodedJWT = verifyToken(getRequest());
        String uuid = decodedJWT.getClaim("uuid").asString();
        return uuid;
    }

    /**
     * 获取登录用户的缓存
     */
    public LoginAdmin getLoginAdminCache(){
        String uuid = getUUID();
        String s = stringRedisTemplate.opsForValue().get(uuid);
        LoginAdmin loginAdmin = JsonUtils.str2Object(s, LoginAdmin.class);
        return loginAdmin;
    }

    /**
     * 获取登录用户的id值
     */
    public Long adminId(){
        Long adminId = getLoginAdminCache().getAdmin().getId();
        return adminId;
    }

    /**
     * 获取用户是否是管理员
     */
    public boolean isAdmin(){
        Boolean isAdmin = getLoginAdminCache().getAdmin().getIsAdmin();
        return isAdmin;
    }

    /**
     * 检查token是否正确
     */
    public boolean checkTokenIsSure(HttpServletRequest httpServletRequest){
        String authentication = httpServletRequest.getHeader("Authetication");
        if(authentication!=null){
            String token = authentication.substring(7);
            //没有传递token
            if("null".equalsIgnoreCase(token)){
                throw new LoginException(AxiosStatus.NO_LOGIN_ERROR);
            }
            //token传递不正确
            if(!authentication.startsWith("Bearer ")){
                throw new LoginException(AxiosStatus.NO_LOGIN_ERROR);
            }
            //判断token的长度
            String[] s = authentication.split(" ");
            if(s.length!=2){
                throw new LoginException(AxiosStatus.NO_LOGIN_ERROR);
            }
            try {
                verifyToken(httpServletRequest);
            }catch (Exception e){
                //解析出现异常，token不正确
                throw new LoginException(AxiosStatus.NO_LOGIN_ERROR);
            }
        }
        return true;
    }

    /**
     * 获取全局request
     */
    public HttpServletRequest getRequest(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
         return requestAttributes.getRequest();
    }

    /**
     * 获取全局response
     */
    public HttpServletResponse getResponse(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getResponse();
    }

}
