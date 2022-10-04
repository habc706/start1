package com.example.shiro_boot.service;

import com.example.shiro_boot.mapper.LikeMapper;
import com.example.shiro_boot.mapper.UserMapper;
import com.example.shiro_boot.pojo.vo.UserRes;

import com.example.shiro_boot.utils.RedisUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class LikeServiceimpl  {

    @Autowired
    private LikeMapper likeMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private UserMapper userMapper;


    //更改喜欢状态，如果没有就插入，有就更改
    @SneakyThrows
    public Integer change_like(Long postid, Long token) {

        Long uuid=redisUtils.getUuid(token);
        Boolean b= likeMapper.quert_like(postid,uuid);

        if (b==null)
            return likeMapper.add_like(postid,uuid);

        if (b){
            return likeMapper.change_like(postid,uuid,false);
        }else
            return likeMapper.change_like(postid,uuid,true);
    }


    public Integer likenums(Long postid) {


        return likeMapper.query_nums(postid);
    }


    public HashMap<Object, Object> who_likes(String postid) {

        List<Long> idlist=likeMapper.who_likes(postid);
        //TODO 根据列表逐个查询,返回token还是uuid？？？
        // id,姓名
        HashMap<Object ,Object>res=new HashMap<>();
        for (Long id
                :idlist
             ) {

            UserRes userRes=userMapper.query_user_info(id);
            res.put(id,userRes);
        }

        return res;
    }


}
