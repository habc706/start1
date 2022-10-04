package com.example.shiro_boot.utils;

import com.example.shiro_boot.config.SnowflakeIdWorker;

public class SnowAlgorithm {

    private  static SnowflakeIdWorker idWorker=new SnowflakeIdWorker(0,0);

    public static long getid(){
        return idWorker.nextId();
    }

}

