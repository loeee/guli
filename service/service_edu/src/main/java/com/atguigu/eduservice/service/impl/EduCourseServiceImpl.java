package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-08-12
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
  @Autowired
  private EduCourseDescriptionService courseDescriptionService;
    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    //添加课程信息的方法
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        if (courseInfoVo.getTitle().length()>25){
            throw  new GuliException(20001,"标题长度过长");
        }
    //1.向课程表中添加课程的基本信息
        //CourseInfoVo对象转换eduCourse对象0
        EduCourse eduCourse=new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);

        if (insert==0){
            throw  new GuliException(20001,"添加课程信息失败");
        }

        //获取添加之后的课程id 实现course表与description表的一对一关系
        String cid=eduCourse.getId();


         //2.向课程简介表添加课程简介
        EduCourseDescription eduCourseDescription=new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());

        //设置描述id等于课程id
        eduCourseDescription.setId(cid);
        courseDescriptionService.save(eduCourseDescription);
        return cid;
    }

    //根据课程号查询基本信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //1.查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //2.查询描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {

        //课程表修改
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update==0){
            throw new GuliException(20001,"修改课程信息失败");
        }

        //修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(description);

    }

    //根据课程id查询课程确认信息
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo publishVo = baseMapper.getCoursePublishVo(id);
        return publishVo;
    }

    //删除课程信息
    @Override
    public void removeCourse(String courseId) {
        this.eduVideoService.removeVideoCourseId(courseId);
       this.eduChapterService.removeChapterCourseId(courseId);
        this.courseDescriptionService.removeById(courseId);
        int res = baseMapper.deleteById(courseId);
        if(res == 0) {
            throw new GuliException(20001,"删除失败");
        }
    }
}
