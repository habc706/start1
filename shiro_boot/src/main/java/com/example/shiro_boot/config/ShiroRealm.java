package com.example.shiro_boot.config;

import com.example.shiro_boot.Excepiton.NotActiveException;
import com.example.shiro_boot.mapper.LoginMapper;
import com.example.shiro_boot.mapper.TokenMapper;
import com.example.shiro_boot.mapper.UserMapper;
import com.example.shiro_boot.pojo.vo.Login_mid;

import com.example.shiro_boot.utils.MD5Utils;
import com.example.shiro_boot.utils.RedisUtils;
import com.example.shiro_boot.utils.SnowAlgorithm;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    LoginMapper loginMapper;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    TokenMapper tokenMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        log.error("执行授权操作");
        info.addStringPermission("auth1"); //最终在数据库中查询权限

   //     Subject subject = SecurityUtils.getSubject();
//        subject.getPrincipal();
//        User user = (User) subject.getPrincipal();
        //info.setStringPermissions(Collections.singleton("auth1"));  // 每个用户的具体权限

        return null;
    }


    @SneakyThrows
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken usertoken = (UsernamePasswordToken) authenticationToken;
        String userName = (String)usertoken.getPrincipal();

        String database_pass = loginMapper.query_passwd(userName);
        String pas = new String((char[]) usertoken.getCredentials());


        log.error("数据库密码"+database_pass);
        //最后默认密码认证,把用户信息存进subject
        if (MD5Utils.passwordIsTrue(pas,database_pass)){
            log.error("到了这里");
            //这里更新redis和token信息
            Long token= SnowAlgorithm.getid();

            Login_mid login_mid = loginMapper.query_message(userName);
            Long uuid = login_mid.getUuid();


            try
            {
                redisUtils.setToken(token,uuid);
            }catch (Exception e){
                log.error("redis存入异常,类型是："+e.getClass()+"具体信息为"+e.getMessage());
                return new SimpleAuthenticationInfo("",pas,"");

            }



            return new SimpleAuthenticationInfo("",pas,"");
        }

        else{
            log.error("用户"+userName+"密码异常");
            throw  new IncorrectCredentialsException();  //密码错误异常
        }

    }
}

