package com.example.shiro_boot.controller;

import com.example.shiro_boot.Excepiton.ExpiraExcetion;
import com.example.shiro_boot.mapper.PostMapper;
import com.example.shiro_boot.pojo.Post;
import com.example.shiro_boot.service.PostServiceimpl;
import com.example.shiro_boot.utils.RedisUtils;
import com.guo.res.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostControler {

    private final PostServiceimpl postServiceimpl;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    RedisUtils redisUtils;

    public PostControler(PostServiceimpl postServiceimpl) {
        this.postServiceimpl = postServiceimpl;
    }

    @PostMapping("/write")
    public Res write_post(String token,String content,String title) throws ExpiraExcetion {

        Long uuid=redisUtils.getUuid(token);
        int a= postServiceimpl.write_post(uuid,content,title);
        if (a==1)
            return Res.ok();
        else
            return Res.fail();
    }

    //查看某一篇具体信息，标题
    @GetMapping("get/{id}")
    public Res get_a_post(@PathVariable("id") Long id){
        Post post = postServiceimpl.get_post(id);

        return Res.ok().data("post",post);
    }

    //返回总数、具体篇章的标题
    @GetMapping("/get_posts")
    public Res get_posts(Integer offset,Integer limit){

        List<Post> res=postServiceimpl.get_posts(offset,limit);
        Integer nums = postMapper.query_num();

        return Res.ok().data("posts",res).data("nums",nums);
    }


    @PostMapping("/my_posts")
    public Res my_post(Long  uuid) {

        return Res.ok().data("posts",postMapper.query_my_posts(uuid));
    }

    @PostMapping("/delete")
    public Res delete(Long postid,String token) throws ExpiraExcetion {
        Long uuid=redisUtils.getUuid(token);
        Long real_user=postMapper.whos_post(postid);
        if (!uuid.equals(real_user))
            return Res.fail().setMessage("身份不匹配，没有权利删除");

        int a= postMapper.delete(postid);
        if (a==1)
            return Res.ok().setMessage("删除成功");
        else
            return Res.fail().setMessage("删除失败");
    }



}
