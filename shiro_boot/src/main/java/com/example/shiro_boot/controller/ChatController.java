package com.example.shiro_boot.controller;

import com.example.shiro_boot.Excepiton.ExpiraExcetion;
import com.example.shiro_boot.mapper.ChatMapper;
import com.example.shiro_boot.mapper.UserMapper;
import com.example.shiro_boot.pojo.vo.ChatRecords;
import com.example.shiro_boot.utils.RedisUtils;
import com.guo.res.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatMapper chatMapper;

    private final RedisUtils redisUtilsl;

    @Autowired
    private UserMapper userMapper;

    public ChatController(ChatMapper chatMapper, RedisUtils redisUtilsl) {
        this.chatMapper = chatMapper;
        this.redisUtilsl = redisUtilsl;
    }

    @PostMapping("/add_chat")
    public Res add_chat(String uuid,String token,String content) throws ExpiraExcetion {  //uuid是对方的uuid
        String myid=redisUtilsl.getUuid(token);
        //生成id
        Date date=userMapper.query_date(myid);
        Date date1=userMapper.query_date(uuid);
        String id=null;
        if (date.compareTo(date1)<0){  //date更早
            id=myid.substring(0,8)+uuid.substring(0,8);
        }else {
            id=uuid.substring(0,8)+myid.substring(0,8);
        }
        chatMapper.add_chat(id,myid,content,new Date());

        return Res.ok();
    }
    @PostMapping("/get_chats")
    public Res get_chats(String uuid,String token) throws ExpiraExcetion {
        String myid=redisUtilsl.getUuid(token);
        Date date=userMapper.query_date(myid);
        Date date1=userMapper.query_date(uuid);
        String id=null;
        if (date.compareTo(date1)<0){  //date更早
            id=myid.substring(0,8)+uuid.substring(0,8);
        }else {
            id=uuid.substring(0,8)+myid.substring(0,8);
        }
        List<ChatRecords> chatRecords = chatMapper.query_chat_records(id);

        return Res.ok().data("records",chatRecords);
    }

}
