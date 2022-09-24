package com.example.shiro_boot.Excepiton;

import com.guo.res.Res;
import com.guo.res.ResCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.PublicKey;

@ControllerAdvice
@Slf4j
public class MyGlobalException extends Throwable {
    @ExceptionHandler(Exception.class)
    @ResponseBody //为了返回数据
    public Res error(Exception e) {
        log.error("执行了全局异常处理，异常类型为"+e.getClass()+"；异常信息："+e.getMessage());
        return Res.fail().setMessage("执行了全局异常处理..").code(ResCode.GLOBALEXCEPTION);
    }

    @ExceptionHandler(ExpiraExcetion.class)
    @ResponseBody
    public Res expirerror(){
        log.error("token时间过期");
        return Res.fail().setMessage("没有这样的token").code(ResCode.TOKEN_NOT_FOUND);
    }
    @ExceptionHandler(NotActiveException.class)
    @ResponseBody
    public Res NotActive(){
        log.error("没有激活");
        return Res.fail().setMessage("该邮箱还没激活").code(ResCode.EMAIL_NOT_ACTIVE);
    }

}
