package com.example.shiro_boot.controller;

import com.example.shiro_boot.mapper.TokenMapper;
import com.example.shiro_boot.mapper.UserMapper;
import com.example.shiro_boot.pojo.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guo.res.Res;
import lombok.extern.slf4j.Slf4j;

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
//import static com.example.shiro_boot.utils.Mail.sendSimpleMail;


@Slf4j
@RestController
public class TestCon{

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TokenMapper tokenMapper;

    @Autowired
    UserMapper userMapper;




    @PostMapping("/t") public Res tea( String user){
     //   log.error("测试goods信息"+goods.getGood_owner()+"名称"+goods.getGood_name());
        log.error("用户信息ss"+user);
        try {

         //   User i1 = userMapper.query_by_id(user);

            return Res.ok().setMessage("修改成功");


        }catch (Exception e){
            log.error("查询错误"+e.getMessage());
            return Res.fail().setMessage("查询语句出错");
        }


    }

    @PostMapping("/tes")  //对象存储相关
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


        return Res.ok().setMessage("需要登陆的页面");

    }

    @GetMapping("/aea")
    public Res dossss() throws JsonProcessingException {


        User user=new User();
        user.setName("黄坚是sb");
        user.setPersonality("我是shabi");
        ObjectMapper objectMapper=new ObjectMapper();
        String json=objectMapper.writeValueAsString(user);

        redisTemplate.opsForValue().set("key:3",json);

        String o = (String) redisTemplate.opsForValue().get("key:3");

        User user1 = objectMapper.readValue(o, User.class);
        return Res.ok().data("aa",user1);//.data("if",date).data("date2",date1).data("yes/not",a);

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

    @GetMapping("/logins")
    public String dos3(String name,String password){
       return "ll";
    }

    @GetMapping("/unauthor")
    public String so(){


        return "没授权";
    }


}
