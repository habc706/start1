package com.example.shiro_boot.controller;


import com.example.shiro_boot.Excepiton.ExpiraExcetion;
import com.example.shiro_boot.service.FollowService;
import com.example.shiro_boot.utils.RedisUtils;
import com.guo.res.Res;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/follow")
public class FollowController {

    @Resource
    FollowService followService;

    @Resource
    RedisUtils redisUtils;

    @PostMapping  //关注/取消关注一个人
    public Res follow(String token,String userid,Boolean follow) throws ExpiraExcetion {

        Long myid=redisUtils.getUuid(token);
        return followService.follow(myid,Long.valueOf(userid),follow);
    }

    @PostMapping("/query_news") //查看所关注的人发布的消息
    public Res query_news(String  token) throws ExpiraExcetion {

        Long uuid = redisUtils.getUuid(token);

        return followService.query_news(uuid);
    }

    @PostMapping("query_if_follow") //是否关注了这个人
    public Res query_follow(String token,String user_id) throws ExpiraExcetion {
        Long myid=redisUtils.getUuid(token);
        return followService.if_follow(myid,Long.valueOf(user_id));
    }


}
