package com.example.shiro_boot.utils;
import com.example.shiro_boot.Excepiton.ExpiraExcetion;
import com.example.shiro_boot.mapper.TokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.example.shiro_boot.utils.RedisConstents.TOKEN_PRE;


// 在我们真实的分发中，或者你们在公司，一般都可以看到一个公司自己封装RedisUtil
    @Component
    public final class RedisUtils {

        @Resource
        private RedisTemplate  redisTemplate;

        @Resource
        TokenMapper tokenMapper;

        public Long getUuid(String token) throws ExpiraExcetion {

            try {
                Long res= (Long) redisTemplate.opsForValue().get(TOKEN_PRE+token);
                if (res==null){
                    //查询数据库有没有，没有就抛出异常
                    Long uuid = tokenMapper.query_uuid(token);
                    if (uuid!=null){
                        redisTemplate.opsForValue().set(TOKEN_PRE+token,uuid,48, TimeUnit.HOURS);
                        return uuid;
                    }else {
                        throw new ExpiraExcetion("没有这样的token");
                    }

                }
                return res;
            }catch (RedisConnectionFailureException redisConnectionFailureException){
                Long uuid = tokenMapper.query_uuid(token);
                if (uuid!=null){
//                    redisTemplate.opsForValue().set(token,uuid,48, TimeUnit.HOURS);
                    return uuid;
                }else {
                    throw new ExpiraExcetion("没有这样的token");
                }

            }

        }

        public void setToken(String  token,Object uuid){

            redisTemplate.opsForValue().set(TOKEN_PRE+token,uuid,72,TimeUnit.HOURS);
        }


    }

