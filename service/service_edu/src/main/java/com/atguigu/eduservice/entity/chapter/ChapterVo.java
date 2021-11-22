package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :nier
 * @description:
 * @date 2019/10/23 0023 10:38
 */
@Data
public class ChapterVo {//章节
    private String id;
    private String title;

    //表示小节
    private List<VideoVo> children=new ArrayList<>();
}
