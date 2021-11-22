package com.atguigu.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author :nier
 * @description:
 * @date 2019/10/23 0023 10:38
 */
@Data
public class DemoData {

    @ExcelProperty(value = "学生编号",index = 0) //index表示在表中第几列 value是表头
    private Integer son;

    @ExcelProperty(value = "学生姓名",index = 1)
    private String sname;
}
