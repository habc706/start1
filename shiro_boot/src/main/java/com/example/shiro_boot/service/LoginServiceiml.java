package com.example.shiro_boot.service;

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
import com.example.shiro_boot.utils.RedisUtils;
import com.example.shiro_boot.utils.SnowAlgorithm;
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

import javax.print.DocFlavor;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class LoginServiceiml  {

    @Autowired
    LoginMapper loginMapper;

    @Autowired
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



    //认证成功，返回信息
    public LoginRes logn(Logvo logvo){

            Login_mid login_mid = loginMapper.query_message(logvo.getEmail());
            //查询token
            String  s = tokenMapper.query_token(login_mid.getUuid());
            LoginRes loginRes=new LoginRes();
            loginRes.setName(login_mid.getName());
            loginRes.setToken(s);
            loginRes.setUuid(login_mid.getUuid());
        UserRes userRes= userMapper.query_user_info(login_mid.getUuid());
        loginRes.setPersonality(userRes.getPersonality());
        loginRes.setIcon_url(userRes.getIcon_url());
            //更新token日期或更新token

        return loginRes;


    }




//注册成功返回yes
    @SneakyThrows
    public boolean register(String email, String name, String password) {
        String pass=loginMapper.query_passwd(email);

        if (pass!=null)
            return false;

        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);

        String  token = UUID.randomUUID().toString();


        Long uuid = SnowAlgorithm.getid();

        int min=1000;
        Random random = new Random();//9000
        int active =  (random.nextInt(8998) + min + 1);

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

            User user=new User();
            user.setName(name);user.setUuid(uuid);user.setPassword(encryption_pass);user.setEmail(email);
            user.setCreate_date(new Date());user.setActive_code(active);
            userMapper.add_user(user);


            //添加到redis
            redisUtils.setToken(token,uuid);

            //事务提交
            dataSourceTransactionManager.commit(transactionStatus);//提交
            return true;
        } catch (DuplicateKeyException e) {
            log.error("新增用户时重复主键:" + e.getMessage());
            dataSourceTransactionManager.rollback(transactionStatus);
            throw e;
        }catch (RedisConnectionFailureException redisConnectionException){

            log.error("新增用户时redis出错:" + redisConnectionException.getMessage());
            dataSourceTransactionManager.commit(transactionStatus);//提交
            return true;
        }catch (Exception e){
            log.error("新增用户时出现意外异常:" + e.getMessage()+"异常类型" +   e.getClass());
            dataSourceTransactionManager.rollback(transactionStatus);
            throw e;
        }

    }



}
