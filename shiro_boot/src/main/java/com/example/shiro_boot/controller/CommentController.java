package com.example.shiro_boot.controller;

import com.example.shiro_boot.Excepiton.ExpiraExcetion;
import com.example.shiro_boot.pojo.Comments;
import com.example.shiro_boot.pojo.vo.CommentRes;
import com.example.shiro_boot.service.CommentServiceimpl;
import com.example.shiro_boot.utils.RedisUtils;
import com.guo.res.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentServiceimpl commentServiceimpl;

    @Autowired
    private RedisUtils redisUtils;

    public CommentController(CommentServiceimpl commentServiceimpl) {
        this.commentServiceimpl = commentServiceimpl;
    }

    //增加评论，删除评论，查看所有评论，评论下面还有评论
    @PostMapping("give_coment")
    public Res give_comment(String postid,String token,String content) throws ExpiraExcetion {
        String uuid = redisUtils.getUuid(token);
        commentServiceimpl.add_comment(postid,uuid,content);
        return Res.ok();
    }

    @PostMapping("delete_comment")
    public Res delete_comment(String postid, String token, String date) throws ParseException, ExpiraExcetion {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date2 = ft.parse(date);
        String uuid=redisUtils.getUuid(token);
        //Date转换成string
        commentServiceimpl.delete_comment(postid,uuid,date2);

        return Res.ok();
    }

    @GetMapping("/{postid}")
    public Res get_comments(@PathVariable("postid")String postid){

       List<CommentRes> re= commentServiceimpl.query_comments(postid);
        return Res.ok().data("res",re);

    }

}
