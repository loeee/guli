package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TearchQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-08-04
 */
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {
 @Autowired
 private EduTeacherService eduTeacherService;
    //查询讲师所有数据
    @GetMapping("/findAll")
    @ApiOperation(value = "所有讲师列表")
    public R findAll() {
        List<EduTeacher> list = this.eduTeacherService.list(null);
        return R.ok().data("items",list);
    }

    @DeleteMapping("{id}")
    public R removeTeacher(@PathVariable String id){
        boolean b = eduTeacherService.removeById(id);
        if (b){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //分页查询讲师的方法

    @GetMapping("pageTearch/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,@PathVariable long limit){
        //创建Page对象
        Page<EduTeacher> pageTeacher =new Page<>(current,limit);
        //调用方法实现分页
        //调用方法时候，底层封装，把分页所有数据封装到pageTearch对象里面
        eduTeacherService.page(pageTeacher,null);
        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    //条件查询，分页
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) TearchQuery teacherQuery){

        Page<EduTeacher> page = new Page<>(current,limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper();

        if(!StringUtils.isEmpty(teacherQuery.getName())) {
            wrapper.like("name",teacherQuery.getName());
        }

        if(!StringUtils.isEmpty(teacherQuery.getLevel())) {
            wrapper.eq("level",teacherQuery.getLevel());
        }

        if(!StringUtils.isEmpty(teacherQuery.getBegin())) {
            wrapper.ge("gmt_create",teacherQuery.getBegin());
        }

        if(!StringUtils.isEmpty(teacherQuery.getEnd())) {
            wrapper.le("gmt_modified",teacherQuery.getEnd());
        }
        //排序  降序
        wrapper.orderByDesc("gmt_modified");
        this.eduTeacherService.page(page,wrapper);
        long total = page.getTotal(); //总记录数
        List<EduTeacher> records = page.getRecords();  //list集合

        return R.ok().data("total",total).data("rows",records);
    }

    //添加讲师
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //讲师修改接口
    //首先根据id查询
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }
    //然后修改
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }

    }
}

