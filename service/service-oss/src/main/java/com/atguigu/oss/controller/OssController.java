package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;

import com.atguigu.oss.service.Ossservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author :nier
 * @description:
 * @date 2019/10/23 0023 10:38
 */
@RestController
@RequestMapping("/eduoss/fileOss")
@CrossOrigin
public class OssController {

    @Autowired
   private Ossservice ossservice;

    @PostMapping
    public R uploadOssFile(MultipartFile file){
        //获取上传文件 MutipartFile

        //返回上传到oss的路径
        String url = ossservice.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }
}
