package com.example.shiro_boot.controller;

import com.example.shiro_boot.service.CommentServiceimpl;
import com.guo.res.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentServiceimpl commentServiceimpl;

    public CommentController(CommentServiceimpl commentServiceimpl) {
        this.commentServiceimpl = commentServiceimpl;
    }

    //增加评论，删除评论，查看所有评论，评论下面还有评论
    @PostMapping("give_coment")
    public Res give_comment(String postid,String uuid){

        return Res.ok();
    }

    @PostMapping("delete_comment")
    public Res delete_comment(String postid, String uuid, Date date){

        commentServiceimpl.delete_comment(postid,uuid,date);

        return Res.ok();
    }

    @GetMapping("/{postid}")
    public Res get_comments(@PathVariable("postid")String postid){

        List<HashMap<String, String>> hashMaps = commentServiceimpl.query_comments(postid);

        return Res.ok().data("res",hashMaps);

    }

}
