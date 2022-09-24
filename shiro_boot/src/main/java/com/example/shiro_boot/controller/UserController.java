package com.example.shiro_boot.controller;

import com.example.shiro_boot.mapper.UserMapper;
import com.example.shiro_boot.pojo.User;
import com.guo.res.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    //查询个人信息

    @Autowired
    UserMapper userMapper;

    @PostMapping("/change_info")
    public Res change_info(User user){
      try {
          userMapper.change_user_info(user);
          return Res.ok().data("user",user);
      }catch (Exception e){
          log.error("修改个人信息出错"+e.getMessage());
            return Res.fail();
      }

    }

    @PostMapping("/add_user")
    public Res add_user(User user){
        try {
            userMapper.add_user(user);
            return Res.ok().data("user",user);
        }catch (Exception e){
            log.error("新增用户出错"+e.getMessage());
            return Res.fail();
        }

    }

}
