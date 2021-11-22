package com.atguigu.oss.service.Impl;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.Ossservice;
import com.atguigu.oss.utils.CanstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author :nier
 * @description:
 * @date 2019/10/23 0023 10:38
 */
@Service
public class OssServiceImpl implements Ossservice {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = CanstantPropertiesUtils.END_POINT;
// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = CanstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = CanstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName=CanstantPropertiesUtils.BUCKET_NAME;

        try {
// 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
            InputStream inputStream = file.getInputStream();


            String fileName = file.getOriginalFilename();

            //在文件名称里面添加随机唯一的值
            String uuid= UUID.randomUUID().toString().replaceAll("-","");
            //把uuid加到文件名称中去
            fileName=uuid+fileName;
            //把文件按日期分类
            String DatePath=new DateTime().toString("yyyy/MM/dd");
            //拼接进文件名称里
            fileName=DatePath+"/"+fileName;

// 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
                                          //fileName--上传到oss文件路径和文件名称     inputStream--上传文件到输入流
            ossClient.putObject(bucketName, fileName, inputStream);



// 关闭OSSClient。
            ossClient.shutdown();

            //需要把上传到阿里云oss路径手动拼接出来
            String url="https://"+bucketName+"."+endpoint+"/"+fileName;
            return url;

        }catch (Exception e){

        }

        return null;
    }
}
