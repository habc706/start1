package com.example.shiro_boot.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Follow {
    private String user_id;
    private String fllow_user_id;
    private Date create_time;

}
