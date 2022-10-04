package com.example.shiro_boot.service;

import com.example.shiro_boot.mapper.PostMapper;
import com.example.shiro_boot.pojo.Post;

import com.example.shiro_boot.utils.SnowAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceimpl  {
    @Autowired
    private PostMapper postMapper;


    public List<Post> get_posts(Integer offset, Integer limit) {

        List<Post> res=postMapper.query_posts(offset,limit);
        return res;
    }


    public Post get_post(Long postid) {
        Post post = postMapper.query_post(postid);

        return post;
    }


    public Integer write_post(Long uuid, String content, String title) {
        Post post=new Post(uuid,content,title);
        post.setDate(new Date());
        //生成postid 前10位用户id+后6位时间戳

        Long postid= SnowAlgorithm.getid();
        post.setPostid(postid);
        Integer res =postMapper.add_post(post);

        return res;
    }
}
