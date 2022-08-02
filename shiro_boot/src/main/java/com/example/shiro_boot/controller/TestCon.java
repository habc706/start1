package com.example.shiro_boot.controller;

import com.example.shiro_boot.mapper.GoodMapper;
import com.example.shiro_boot.mapper.TokenMapper;
import com.example.shiro_boot.mapper.UserMapper;
import com.example.shiro_boot.pojo.Goods;
import com.example.shiro_boot.pojo.Token;
import com.example.shiro_boot.pojo.User;
import com.guo.res.Res;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
public class TestCon {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    GoodMapper good;

    @Autowired
    TokenMapper tokenMapper;

    @Autowired
    UserMapper userMapper;

    @PostMapping("/t") public Res tea( String user){
     //   log.error("测试goods信息"+goods.getGood_owner()+"名称"+goods.getGood_name());
        log.error("用户信息ss"+user);
        try {

            User i1 = userMapper.query_by_id(user);

            return Res.ok().setMessage("修改成功").data("d",i1);


        }catch (Exception e){
            log.error("查询错误"+e.getMessage());
            return Res.fail().setMessage("查询语句出错");
        }


    }

    @PostMapping("/tes")
    public Res redit(MultipartFile file,String token) throws IOException {
        String url = "http://localhost:8002/eduoss/fileoss/";

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>(1);

        Resource resource = new ByteArrayResource(file.getBytes()){
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };


        params.add("file",resource);
        Res res = restTemplate.postForObject(url, params, Res.class);
        // ResponseEntity<Res> responseEntity = restTemplate.postForObject();


        try {

            return Res.ok().setMessage(token).data(res.getData());
        }catch (Exception e){
            log.error("转发时错误："+e.getMessage());
            return  Res.fail();

        }



    }

    @GetMapping("/a")
    public Res dos(){




     //   redisTemplate.opsForValue().set("teas",se);
        log.error("测试日志信息22222");
        return Res.ok().code(12);
    }

    @GetMapping("/redis/get/{key}")
    public Object dossa(@PathVariable("key") String key){

        return  redisTemplate.opsForValue().get(key);

    }

    @GetMapping("/redis/set/{key}/{value}")
    public Object setkey(@PathVariable("value") String value,@PathVariable("key") String key){

        redisTemplate.opsForValue().set(key,value);
        return "okk";
    }

    @GetMapping("/")
    public String dos2(){
        return "start";
    }

    @GetMapping("/needa")
    public String ne(){
        return "需要授权的页面";
    }

    @GetMapping("/login")
    public String dos3(String name,String password){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);

        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            System.out.println("There is no user with username of " + token.getPrincipal());
            return "用户名不存在";
        } catch (IncorrectCredentialsException ice) {
            System.out.println("Password for account " + token.getPrincipal() + " was incorrect!");
            return "秘密错误";
        } catch (LockedAccountException lae) {
            System.out.println("The account for username " + token.getPrincipal() + " is locked.  " +
                    "Please contact your administrator to unlock it.");
            return "用户被锁定";
        }
        // ... catch more exceptions here (maybe custom ones specific to your application?
        catch (AuthenticationException ae) {
            //unexpected condition?  error?
            return "其他异常";
        }
        return "Login Successfully";
    }

    @GetMapping("/unauthor")
    public String so(){


        return "没授权";
    }


}
