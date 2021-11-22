package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Abner
 * @since 2020-06-29
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

  @Override
  public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
    try {
      InputStream in = file.getInputStream();
      EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
    }catch (Exception e) {
      e.printStackTrace();
    }
  }


//课程分类列表

  @Override
  public List<OneSubject> getAllOneTwoSubject() {

//查询所有一级分类 parent_id=0
    QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
    wrapperOne.eq("parent_id","0");
    List<EduSubject> oneSubjectList=baseMapper.selectList(wrapperOne);


//查询所有二级分类 parent_id！=0
    QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
    wrapperTwo.ne("parent_id","0");
    List<EduSubject> twoSubjectLists = baseMapper.selectList(wrapperTwo);

    //创建list集合，用于存储最终封装数据
    List<OneSubject> finalSubjectList=new ArrayList<>();

    //封装一级分类
//查询出来所有的一级分类list集合遍历，得到每一个一级分类对象，获取每一个一级分类对象值
    //封装到要求的list集合里面 List<OneSubject> finalSubjectList
    for (int i = 0; i < oneSubjectList.size(); i++) {//遍历循环 oneSubjectList

      //oneSubjectList每个值放到eduSubject对象里
      EduSubject eduSubject=oneSubjectList.get(i);

      //把eduSubject的值获取出来，放到OnrSubject对象里
      //多个OneSubject放到finalsUBJECTlIST里
      OneSubject oneSubject = new OneSubject();
//      oneSubject.setId(eduSubject.getId());
//      oneSubject.setTitle(eduSubject.getTitle());
      BeanUtils.copyProperties(eduSubject,oneSubject);

      finalSubjectList.add(oneSubject);

      //在一级分类循环遍历查询所有的二级分类
      //创建list集合封装每一个一级分类的二级分类
      ArrayList<TwoSubject> twoFinalSubjectList = new ArrayList<>();
      for (int m = 0; m < twoSubjectLists.size(); m++) {
        EduSubject tSubject =twoSubjectLists.get(m);
        //判断tSubject值复制到TwoSubject里面，放到twofinalSubject里面
        if (tSubject.getParentId().equals(eduSubject.getId())){
          TwoSubject twoSubject=new TwoSubject();
          BeanUtils.copyProperties(tSubject,twoSubject);
          twoFinalSubjectList.add(twoSubject);
        }
      }
      //把一级西面所有二级分类放到一级分类里面
      oneSubject.setChildren(twoFinalSubjectList);
    }
    
    
    //封住二级分类



    return finalSubjectList;
  }
}
