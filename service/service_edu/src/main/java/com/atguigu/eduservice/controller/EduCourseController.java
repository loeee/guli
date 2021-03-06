package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-08-12
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    //添加课程基本信息的方法
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String id=courseService.saveCourseInfo(courseInfoVo);

        return R.ok().data("courseId",id);
    }

    //根据课程号查询基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo=courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("getPulishCourseInfo/{id}")
    public R getPulishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo= courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }

    //修改课程状态
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }

    //课程列表
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable long current, @PathVariable long limit,
                                 @RequestBody(required = false) CourseQuery courseQuery) {


        Page<EduCourse> page = new Page<>(current,limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper();

        if(!StringUtils.isEmpty(courseQuery.getTitle())) {
            wrapper.like("title",courseQuery.getTitle());
        }

        if(!StringUtils.isEmpty(courseQuery.getStatus())) {
            wrapper.eq("status",courseQuery.getStatus());
        }

        if(!StringUtils.isEmpty(courseQuery.getBegin())) {
            wrapper.ge("gmt_create",courseQuery.getBegin());
        }

        if(!StringUtils.isEmpty(courseQuery.getEnd())) {
            wrapper.le("gmt_modified",courseQuery.getEnd());
        }

        this.courseService.page(page,wrapper);
        long total = page.getTotal(); //总记录数
        List<EduCourse> records = page.getRecords();  //list集合

        return R.ok().data("total",total).data("rows",records);
    }
    //删除课程
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        this.courseService.removeCourse(courseId);
        return R.ok();
    }
}

