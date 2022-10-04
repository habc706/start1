package com.example.shiro_boot.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
public class Token {
    private Long token;
    private Long uuid;

    private Date add_time;
    private Date expiration_time;

}
