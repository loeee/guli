package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author :nier
 * @description:
 * @date 2019/10/23 0023 10:38
 */
public interface Ossservice {
    //上传头像到oss
    String uploadFileAvatar(MultipartFile file);
}
