package com.example.shiro_boot.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.example.shiro_boot.mapper.LoginMapper;
import com.example.shiro_boot.mapper.TokenMapper;
import com.example.shiro_boot.mapper.UserMapper;
import com.example.shiro_boot.pojo.Token;
import com.example.shiro_boot.pojo.User;
import com.example.shiro_boot.pojo.vo.LoginRes;
import com.example.shiro_boot.pojo.vo.Login_mid;
import com.example.shiro_boot.pojo.vo.Logvo;

import com.example.shiro_boot.pojo.vo.UserRes;
import com.example.shiro_boot.utils.MD5Utils;
import com.example.shiro_boot.utils.RedisConstents;
import com.example.shiro_boot.utils.RedisUtils;
import com.example.shiro_boot.utils.SnowAlgorithm;
import com.guo.res.Res;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class LoginServiceiml  {

    @Autowired
    LoginMapper loginMapper;

    @Resource
    TokenMapper tokenMapper;
    @Autowired
    DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    TransactionDefinition transactionDefinition;

    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisUtils redisUtils;

    @Resource
    RedisTemplate redisTemplate;



    //认证成功，返回信息
    public LoginRes logn(Logvo logvo){

            Login_mid login_mid = loginMapper.query_message(logvo.getEmail());
            //查询token
            String  s = tokenMapper.query_token(login_mid.getUuid());
            LoginRes loginRes=new LoginRes();
            loginRes.setName(login_mid.getName());
            //loginRes.setToken(s);
            loginRes.setUuid(login_mid.getUuid());
        UserRes userRes= userMapper.query_user_info(login_mid.getUuid());
        loginRes.setPersonality(userRes.getPersonality());
        loginRes.setIcon_url(userRes.getIcon_url());
        loginRes.setToken(s);
            //更新token日期或更新token

        return loginRes;


    }




//注册成功返回yes
    @SneakyThrows
    public Res register(String email, String password) {
        String pass=loginMapper.query_passwd(email);

        if (pass!=null)
            return Res.fail().setMessage("用户已存在");

        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);

        String  token = UUID.randomUUID().toString();
        Long uuid = SnowAlgorithm.getid();

        try {
            //开启事务

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 1);
            //添加到token表
            Token token1 = new Token();
            token1.setToken(token);

            token1.setUuid(uuid);
            token1.setAdd_time(new Date());

            token1.setExpiration_time(new Date(String.valueOf(calendar.getTime())));
            tokenMapper.add_token(token1);

            String encryption_pass=MD5Utils.string2MD5(password);
            //添加到用户表  uuid,name,encryption_pass,email,active

            //生成随机用户名
            User user=new User();
            user.setName("user_"+RandomUtil.randomString(6));user.setUuid(uuid);user.setPassword(encryption_pass);user.setEmail(email);
            user.setCreate_date(new Date());
            userMapper.add_user(user);


            //添加到redis
            redisUtils.setToken(token,uuid);

            //事务提交
            dataSourceTransactionManager.commit(transactionStatus);//提交
            return Res.ok().setMessage("注册成功");
        } catch (DuplicateKeyException e) {
            log.error("新增用户时重复主键:" + e.getMessage());
            dataSourceTransactionManager.rollback(transactionStatus);
            throw e;
        }catch (RedisConnectionFailureException redisConnectionException){

            log.error("新增用户时redis出错:" + redisConnectionException.getMessage());
            dataSourceTransactionManager.commit(transactionStatus);//提交
            return Res.ok().setMessage("注册成功");
        }catch (Exception e){
            log.error("新增用户时出现意外异常:" + e.getMessage()+"异常类型" +   e.getClass());
            dataSourceTransactionManager.rollback(transactionStatus);
            throw e;
        }

    }


}
