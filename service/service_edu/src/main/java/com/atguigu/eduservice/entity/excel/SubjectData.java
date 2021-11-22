package com.atguigu.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author :nier
 * @description:
 * @date 2019/10/23 0023 10:38
 */
@Data
public class SubjectData {

    @ExcelProperty(index = 0) //表示这是第一列
    private String oneSubjectName;

    @ExcelProperty(index = 1) //表示这是第二列
    private String twoSubjectName;
}
