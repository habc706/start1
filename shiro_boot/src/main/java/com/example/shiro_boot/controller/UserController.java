package com.example.shiro_boot.controller;

import com.example.shiro_boot.Excepiton.ExpiraExcetion;
import com.example.shiro_boot.mapper.UserMapper;
import com.example.shiro_boot.pojo.User;
import com.example.shiro_boot.pojo.vo.UserRes;
import com.example.shiro_boot.utils.RedisUtils;
import com.guo.res.Res;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    //查询个人信息

    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisUtils redisUtils;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/{uuid}")  //直接根据uuid获取
    public Res query_user(@PathVariable("uuid") String uuid){

        UserRes userRes = userMapper.query_user_info(uuid);

        return Res.ok().data("name",userRes.getName()).data("icon",userRes.getIcon_url())
                .data("personality",userRes.getPersonality());
    }


    //用户可以更改的只有：1、昵称 2、图片 3、个性签名  ，除了图片需要特殊处理，其他的一样

    @PostMapping("/change_info")
    public Res change_info(String token,String name,String personnality) throws ExpiraExcetion {
        String uuid=redisUtils.getUuid(token);
        userMapper.change_user_info(uuid,name,personnality);

        return Res.ok();
    }


    @SneakyThrows
    @PostMapping("/change_icon")
    public Res change_icon(MultipartFile file,String token){
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>(1);
        String uuid=redisUtils.getUuid(token);
        params.add("file",file);
        Res res = restTemplate.postForObject("http://localhost:8002/eduoss/fileoss", params,Res.class);
        if (res.getCode()==200){

            String icon= (String) res.getData().get("url");
            userMapper.update_icon(uuid,icon);
            return Res.ok().setMessage("上传成功");

        }else
            return Res.fail().setMessage("上传失败");


    }

}
