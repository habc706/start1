package com.example.shiro_boot.pojo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ChatRecords {
    private String content;
    private Date date;
    private String who;  //uuid

}
