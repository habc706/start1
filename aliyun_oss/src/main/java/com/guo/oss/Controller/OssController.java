package com.guo.oss.Controller;


import com.guo.oss.service.OssService;
import com.guo.res.Res;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    //上传头像的方法
    @PostMapping
    public Res uploadOssFile(MultipartFile file) {
        //获取上传文件  MultipartFile
        //返回上传到oss的路径
        try {
            String url = ossService.uploadFileAvatar(file);
            return Res.ok().data("url",url);
        }
        catch (Exception e){
            return Res.fail();
        }



    }

    @GetMapping("/t")
    public Res de(){
        return Res.ok().data("a","allk");
    }

    @PostMapping("/te")
    public Res desa(String  te){
        return Res.ok().data("posta","postallk: "+te);
    }

}
