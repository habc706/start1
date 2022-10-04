package com.example.shiro_boot.controller;

import com.example.shiro_boot.service.LikeServiceimpl;
import com.guo.res.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeServiceimpl likeServiceimpl;
    //返回点赞量、收藏量、点赞用户信息
    @PostMapping("/details/{postid}")
    public Res like_nums(@PathVariable("postid") Long postid){
        int num=likeServiceimpl.likenums(postid);


        return Res.ok().data("nums",num);
    }

    @PostMapping("/change_like")
    public Res change_like(Long postid,Long token){

        likeServiceimpl.change_like(postid,token);
        return Res.ok();
    }

    @PostMapping("/who_likes")
    public Res who_likes(String postid){
        HashMap<Object, Object> stringStringHashMap = likeServiceimpl.who_likes(postid);
        return Res.ok().data(stringStringHashMap);
    }

}
