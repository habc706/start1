package com.example.shiro_boot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class ShiroBootApplicationTests {

    @Test
    void contextLoads() {
        Date date = new Date();
        long datetime =  date.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        String str = sdf.format(datetime);
        System.out.println(str);
    }

}
