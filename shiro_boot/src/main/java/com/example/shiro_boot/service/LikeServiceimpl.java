package com.example.shiro_boot.service;

import com.example.shiro_boot.mapper.UserMapper;
import com.example.shiro_boot.pojo.vo.UserRes;

import com.example.shiro_boot.utils.RedisConstents;
import com.example.shiro_boot.utils.RedisUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class LikeServiceimpl  {


    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    

    //更改喜欢状态，如果没有就插入，有就更改

    @SneakyThrows
    public Integer change_like(String postid, String token) {

        Long uuid=redisUtils.getUuid(token);
        String key= RedisConstents.GVIE_LIKE+postid;
        String s = String.valueOf(uuid);
        Boolean add = redisTemplate.opsForZSet().add(key, s, System.currentTimeMillis());

        if (add)
            return 1;
        else {
            redisTemplate.opsForZSet().remove(key, uuid);
            return 1;
        }

    }





    public HashMap<Object, Object> who_likes(String postid) {

        String key= RedisConstents.GVIE_LIKE+postid;
        Set<ZSetOperations.TypedTuple<String>> typedTuples = redisTemplate.opsForZSet()
                .reverseRangeByScoreWithScores(key, 0, System.currentTimeMillis(), 0, 100);

        HashMap<Object ,Object>res=new HashMap<>(typedTuples.size());
        if (typedTuples == null || typedTuples.isEmpty()) {
            log.error("therE os sth error ");
            return null;
        }


        for (ZSetOperations.TypedTuple<String> id: typedTuples
        ) {

            String value = id.getValue();


            Long aLong = Long.valueOf(id.getValue());


            UserRes userRes = userMapper.query_user_info(aLong);
            res.put(id.getValue(),userRes);
        }


        return res;
    }


}
