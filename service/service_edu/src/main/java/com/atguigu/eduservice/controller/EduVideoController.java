package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-08-12
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    //添加小节
    @PostMapping("addVideo")
  public R addVideo(@RequestBody EduVideo eduVideo){
      videoService.save(eduVideo);
      return R.ok();
  }
    //删除小节
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
       videoService.removeById(id);
        return R.ok();
    }

    //修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        this.videoService.updateById(eduVideo);
        return R.ok();
    }

}

