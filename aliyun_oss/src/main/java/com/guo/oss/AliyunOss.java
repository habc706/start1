package com.guo.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.guo.oss")
public class AliyunOss {
    public static void main(String[] args) {
        SpringApplication.run(AliyunOss.class,args);
    }
}
