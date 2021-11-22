package com.atguigu.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :nier
 * @description:
 * @date 2019/10/23 0023 10:38
 */
    @Data
    public class OneSubject {

        private String id;

        private String title;

        //一个一级分类里面有多个二级分类
        private List<TwoSubject> children = new ArrayList<>();
    }


