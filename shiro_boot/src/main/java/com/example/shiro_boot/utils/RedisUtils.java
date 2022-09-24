package com.example.shiro_boot.utils;
import com.example.shiro_boot.Excepiton.ExpiraExcetion;
import com.example.shiro_boot.mapper.TokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


// 在我们真实的分发中，或者你们在公司，一般都可以看到一个公司自己封装RedisUtil
    @Component
    public final class RedisUtils {

        @Autowired
        private RedisTemplate  redisTemplate;

        @Autowired
        TokenMapper tokenMapper;

        public String getUuid(String token) throws ExpiraExcetion {
            String res= (String) redisTemplate.opsForValue().get(token);
            if (res==null){
                //查询数据库有没有，没有就抛出异常
                String uuid = tokenMapper.query_uuid(token);
                if (uuid!=null){
                    redisTemplate.opsForValue().set(token,uuid,48, TimeUnit.HOURS);
                    return uuid;
                }else {
                    throw new ExpiraExcetion("没有这样的token");
                }

            }
            return res;

        }

        public void setToken(String  token,String uuid){

            redisTemplate.opsForValue().set(token,uuid,48,TimeUnit.HOURS);
        }


    }
