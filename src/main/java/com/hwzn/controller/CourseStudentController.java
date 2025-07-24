package com.hwzn.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.Result;
import com.hwzn.pojo.dto.common.IdDto;
import com.hwzn.pojo.dto.courseStudent.AddCourseStudentDto;
import com.hwzn.pojo.dto.courseStudent.FilterCourseStudentListDto;
import com.hwzn.pojo.entity.*;
import com.hwzn.service.*;
import com.hwzn.util.JWTUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * @Author: hy
 * @Date: 2025/7/17 13:27
 * @Desc: 课程学生
 */
@RestController
@RequestMapping("/courseStudent")
public class CourseStudentController {

    @Resource
    CourseStudentService courseStudentService;

    @Resource
    CourseTermService courseTermService;

    @Resource
    CourseService courseService;

    @Resource
    UserService userService;

    @Resource
    CourseClassService courseClassService;

    //筛选课程学员列表
    @PostMapping("/filterCourseStudentList")
    public Result filterCourseStudentList(@Validated @RequestBody FilterCourseStudentListDto filterCourseStudentListDto, HttpServletRequest request){
        System.out.println("filterCourseStudentListDto:"+filterCourseStudentListDto);
        String account = JWTUtil.getAccount(request.getHeader("token"));
        Integer role = JWTUtil.getRole(request.getHeader("token"));

        CourseEntity courseEntity = courseService.getCourseById(filterCourseStudentListDto.getCourseId());

        if(courseEntity==null){
            return Result.showInfo(2,"指定课程不存在",null);
        }
        CourseTermEntity courseTermEntity = courseTermService.getCourseTermById(filterCourseStudentListDto.getTermId());

        if(courseTermEntity==null){
            return Result.showInfo(2,"指定课程期次不存在",null);
        }

        if(role != 1 && !StrUtil.equals(courseEntity.getChargerAccount(), account) && (!courseEntity.getAssistants().contains(Objects.requireNonNull(account)) || !courseEntity.getAssistantAuthority().contains("student-management"))){
            return Result.showInfo(2,"仅限管理员和课程负责人及含有相关权限的助教可以操作",null);
        }
        IPage<CourseStudentEntity> resultList = courseStudentService.filterCourseStudentList(filterCourseStudentListDto);
        return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
    }

    //添加课程学员
    @PostMapping("/addCourseStudent")
    @Transactional
    public Result addCourseStudent(@Validated @RequestBody AddCourseStudentDto addCourseStudentDto, HttpServletRequest request){
        if(addCourseStudentDto.getAccount().isEmpty()){
            return Result.showInfo(5,"学员账号不能为空",null);
        }
        //添加学员的时候，学员所在班级第一次在课程期次下，则跟着新增
        UserEntity userEntity = userService.getUserInfoByAccount(addCourseStudentDto.getAccount());
        if(userEntity.getRole() != 3){
            return Result.showInfo(5,"非学员不能加入课程",null);
        }
        CourseClassEntity entity = new CourseClassEntity();
        entity.setTermId(addCourseStudentDto.getTermId());
        entity.setCourseId(addCourseStudentDto.getCourseId());
        entity.setCode(userEntity.getClassCode());
        IPage<CourseClassEntity> courseClassList = courseClassService.getCourseClassByEntity(entity);

        if(courseClassList.getRecords().isEmpty()){
            CourseClassEntity courseClassEntity = new CourseClassEntity();
            BeanUtils.copyProperties(addCourseStudentDto,courseClassEntity);
            courseClassEntity.setCode(userEntity.getClassCode());
            courseClassService.addCourseClass(courseClassEntity);
        }
        CourseStudentEntity courseStudentEntity = new CourseStudentEntity();
        BeanUtils.copyProperties(addCourseStudentDto,courseStudentEntity);
        //添加课程学员
        courseStudentEntity.setJoinMethod(1);//教师添加
        courseStudentService.addCourseStudent(courseStudentEntity);
        JSONObject data = new JSONObject();
        data.set("id",courseStudentEntity.getId());
        if(courseStudentEntity.getId() == null){
            return Result.showInfo(5,"创建失败",null);
        }
        return Result.showInfo(0,"Success",data);
    }

    //移除课程学员
    @PostMapping("/removeCourseStudent")
    @Transactional
    public Result removeCourseStudent(@Validated @RequestBody IdDto idDto, HttpServletRequest request){
        // 移除学员的时候，该学员的班级在此课程期次下没有别的学员了则班级一起移除
        CourseStudentEntity courseStudentEntity = courseStudentService.getCourseStudentById(idDto.getId());

        UserEntity userEntity = userService.getUserInfoByAccount(courseStudentEntity.getAccount());
        courseStudentEntity.setCode(userEntity.getClassCode());
        List<CourseStudentEntity> entityList = courseStudentService.getCourseStudentByEntity(courseStudentEntity);
        if(entityList.size()==1){
            CourseClassEntity courseClassEntity = new CourseClassEntity();
            BeanUtils.copyProperties(courseStudentEntity,courseClassEntity);
            IPage<CourseClassEntity> entityPage = courseClassService.getCourseClassByEntity(courseClassEntity);
            courseClassService.removeCourseClass(entityPage.getRecords().get(0).getId());
        }
        courseStudentService.removeCourseStudent(idDto.getId());//删除课程
        return Result.showInfo(0,"Success", null);
    }

    //加入课程
    @PostMapping("/joinCourse")
    @Transactional
    public Result joinCourse(@Validated @RequestBody AddCourseStudentDto addCourseStudentDto, HttpServletRequest request){
        String account = JWTUtil.getAccount(request.getHeader("token"));
        Integer role = JWTUtil.getRole(request.getHeader("token"));
        if(role!=3){
            return Result.showInfo(5,"只有学生能加入课程",null);
        }
        // method为2，学员加入的时候，学员所在班级第一次在课程期次下，则跟着新增
        UserEntity userEntity = userService.getUserInfoByAccount(account);
        CourseClassEntity entity = new CourseClassEntity();
        entity.setCode(userEntity.getClassCode());
        entity.setCourseId(addCourseStudentDto.getCourseId());
        entity.setTermId(addCourseStudentDto.getTermId());
        IPage<CourseClassEntity> courseClassList = courseClassService.getCourseClassByEntity(entity);

        if(courseClassList.getRecords().isEmpty()){
            CourseClassEntity courseClassEntity = new CourseClassEntity();
            BeanUtils.copyProperties(addCourseStudentDto,courseClassEntity);
            courseClassEntity.setCode(userEntity.getClassCode());
            courseClassService.addCourseClass(courseClassEntity);
        }

        CourseStudentEntity courseStudentEntity = new CourseStudentEntity();
        BeanUtils.copyProperties(addCourseStudentDto,courseStudentEntity);
        //添加课程学员
        courseStudentEntity.setJoinMethod(2);//自主学习
        courseStudentEntity.setAccount(account);
        courseStudentService.addCourseStudent(courseStudentEntity);
        JSONObject data = new JSONObject();
        data.set("id",courseStudentEntity.getId());
        if(courseStudentEntity.getId() == null){
            return Result.showInfo(5,"创建失败",null);
        }
        return Result.showInfo(0,"Success",data);
    }
}