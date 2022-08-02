package com.example.shiro_boot;

import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@MapperScan("com.example.shiro_boot.mapper")
@SpringBootApplication
public class ShiroBootApplication   {


    public static void main(String[] args) {
         SpringApplication.run(ShiroBootApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }


}
