package com.atguigu.eduservice.entity.vo;

import lombok.Data;

/**
 * @author :nier
 * @description:
 * @date 2019/10/23 0023 10:38
 */
@Data
public class CoursePublishVo {
    private String id;

    private String title;

    private String cover;

    private Integer lessonNum;

    private String subjectLevelOne;

    private String subjectLevelTwo;

    private String teacherName;

    private String price;
}
