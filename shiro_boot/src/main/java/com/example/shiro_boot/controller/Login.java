package com.example.shiro_boot.controller;


import com.example.shiro_boot.mapper.LoginMapper;
import com.example.shiro_boot.mapper.TokenMapper;
import com.example.shiro_boot.mapper.UserMapper;
import com.example.shiro_boot.pojo.User;
import com.example.shiro_boot.pojo.vo.LoginRes;
import com.example.shiro_boot.pojo.vo.Logvo;
import com.example.shiro_boot.service.LoginServiceiml;
import com.example.shiro_boot.utils.Mail;
import com.example.shiro_boot.utils.RedisConstents;
import com.guo.res.Res;
import com.guo.res.ResCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/login")
public class Login {


    @Autowired
    LoginServiceiml loginService;


    @Autowired
    private Mail mail; //发邮箱

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private TokenMapper tokenMapper;
    @Resource
    RedisTemplate redisTemplate;



//登陆并返回token
    @PostMapping("/login")
    public Res login(Logvo logvo){
        if (logvo==null||logvo.getPasswd()==null||logvo.getEmail()==null)
            return Res.fail().setMessage("请先登陆").code(ResCode.NOTLOGIN);


        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(logvo.getEmail(), logvo.getPasswd());
        token.setRememberMe(true);

        if(loginMapper.query_message(logvo.getEmail())==null){
            return Res.fail().setMessage("邮箱不存在");  //返回空值就是用户名不存在
        }


        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            log.error("There is no user with username of " + token.getPrincipal());
            return Res.fail().setMessage("邮箱不存在");
        } catch (IncorrectCredentialsException ice) {
            log.error("Password for account " + token.getPrincipal() + " was incorrect!");
            return Res.fail().setMessage("密码错误");
        } catch (LockedAccountException lae) {
            log.error("The account for username " + token.getPrincipal() + " is locked.  " +
                    "Please contact your administrator to unlock it.");
            return Res.fail().setMessage("用户被锁定");
        }
        // ... catch more exceptions here (maybe custom ones specific to your application?
        catch (AuthenticationException ae) {
            //unexpected condition?  error?
            log.error(ae.getMessage());
            return Res.fail().setMessage("系统发生了其他异常");
        }

        //根据昵称密码返回是否正确

        LoginRes jud = loginService.logn(logvo);
        HashMap<Object ,Object> hashMap=new HashMap<>();
        hashMap.put("name",jud.getName());
        hashMap.put("uuid",jud.getUuid());
        hashMap.put("icon",jud.getIcon_url());
        hashMap.put("personality",jud.getPersonality());
        hashMap.put("token",jud.getToken());


        return Res.ok().data(hashMap);


    }

    //如果返回的是fail就显示信息，否则取出token和用户名
    @GetMapping("/login")
    public Res logins(Logvo logvo){
        if (logvo==null||logvo.getPasswd()==null||logvo.getEmail()==null)
            return Res.fail().setMessage("请先登陆").code(ResCode.NOTLOGIN);


        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(logvo.getEmail(), logvo.getPasswd());
//        token.setRememberMe(true);

        if(loginMapper.query_message(logvo.getEmail())==null){
            return Res.fail().setMessage("邮箱不存在");  //返回空值就是用户名不存在
        }


        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            log.error("There is no user with username of " + token.getPrincipal());
            return Res.fail().setMessage("邮箱不存在");
        } catch (IncorrectCredentialsException ice) {
            log.error("Password for account " + token.getPrincipal() + " was incorrect!");
            return Res.fail().setMessage("密码错误");
        } catch (LockedAccountException lae) {
            log.error("The account for username " + token.getPrincipal() + " is locked.  " +
                    "Please contact your administrator to unlock it.");
            return Res.fail().setMessage("用户被锁定");
        }
        // ... catch more exceptions here (maybe custom ones specific to your application?
        catch (AuthenticationException ae) {
            //unexpected condition?  error?
            log.error(ae.getMessage());
            return Res.fail().setMessage("系统发生了其他异常");
        }

        //根据昵称密码返回是否正确
        LoginRes jud = loginService.logn(logvo);
        HashMap<Object ,Object> hashMap=new HashMap<>();
        hashMap.put("name",jud.getName());
        hashMap.put("uuid",jud.getUuid());
        hashMap.put("icon",jud.getIcon_url());
        hashMap.put("personality",jud.getPersonality());

        return Res.ok().data(hashMap);
    }


    //分开一个sendcode和注册
    @PostMapping("/register")  //注册成功后返回成功
    public Res register(String email,String password,String active_code){

        String code= (String) redisTemplate.opsForValue().get(RedisConstents.VERTIFY+email);
        if (!code.equals(active_code)){
            return Res.fail().setMessage("验证码不正确");
        }

        //1.查看是否包含相同的邮箱，如果有
       return loginService.register(email,password);

    }

    //这里发送
    @PostMapping("/sendcode")
    public Res activate(String email){
        int min=1000;
        Random random = new Random();//9000
        int active_code =  (random.nextInt(8998) + min + 1);

        redisTemplate.opsForValue().set(RedisConstents.VERTIFY+email,active_code+"");
        redisTemplate.expire(RedisConstents.VERTIFY+email,5L, TimeUnit.MINUTES);

        mail.sendMail(email,"您的hktalk验证码","您的验证码是："+active_code+"。5分钟后验证码过期");
        return Res.ok().setMessage("注册成功，请查看邮箱验证码激活，验证码时间有效期为5分钟，请注意");

    }


    @PostMapping("/logout")
    public Res logout(){
        Subject subject= SecurityUtils.getSubject();
        subject.logout();
        return Res.ok().setMessage("成功退出登陆");
    }


    //TODO 方便测试用，以后删掉
    @GetMapping("/logout")
    public Res logout2(){
        Subject subject= SecurityUtils.getSubject();
        subject.logout();
        return Res.ok().setMessage("成功退出登陆");
    }
}
