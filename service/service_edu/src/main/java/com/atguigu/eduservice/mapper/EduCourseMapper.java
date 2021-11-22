package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-08-12
 */
@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public default CoursePublishVo getCoursePublishVo(String courseId) {
        return null;
    }
}
