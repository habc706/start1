package com.example.shiro_boot.service;

import com.example.shiro_boot.mapper.FollowMapper;
import com.example.shiro_boot.mapper.PostMapper;
import com.example.shiro_boot.mapper.UserMapper;
import com.example.shiro_boot.pojo.Post;
import com.example.shiro_boot.pojo.User;
import com.example.shiro_boot.pojo.vo.FollowPost;
import com.example.shiro_boot.pojo.vo.UserRes;
import com.example.shiro_boot.utils.RedisConstents;
import com.guo.res.Res;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Set;

@Service
public class FollowService {

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    FollowMapper followMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    PostMapper postMapper;

    public Res query_news(Long uuid) {  //我所关注的人发了的消息

        String key= RedisConstents.FOLLOW+uuid;
        Set<ZSetOperations.TypedTuple<String>> typedTuples  = redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, 0, System.currentTimeMillis(),
                0, 100);
        if (typedTuples==null||typedTuples.isEmpty())
            return Res.ok();

        HashMap<Object ,Object> res=new HashMap<>(typedTuples.size());
        for (ZSetOperations.TypedTuple<String> element:typedTuples
             ) {
            Long postid = Long.valueOf(element.getValue());//每一个Postid
            Post post=postMapper.query_post(postid);
            UserRes user=userMapper.query_user_info(post.getOwner());

            FollowPost followPost=new FollowPost();
            followPost.setDate(post.getDate());followPost.setOwner(post.getOwner());
            followPost.setOwner_img(user.getIcon_url()); followPost.setTitle(post.getTitle());
            followPost.setOwner_name(user.getName());

            res.put(postid,followPost);
        }
        return Res.ok().data(res);

    }

    public Res follow(Long myid, Long user_id, Boolean follow) {
        if (follow){  //要关注这个人

            Integer insert = followMapper.insert(myid, user_id);
            if (insert>0)
                return Res.ok().setMessage("关注成功");

            return Res.fail().setMessage("关注失败");

        }else {

            Integer insert = followMapper.delete(myid, user_id);
            if (insert>0)
                return Res.ok().setMessage("取消关注成功");

            return Res.fail().setMessage("取消关注失败");
        }


    }


    public Res if_follow(Long myid, Long aLong) {

        return followMapper.query_if(myid,aLong)==0?Res.fail().setMessage("没有关注"):Res.ok().setMessage("已经关注了");

    }
}
