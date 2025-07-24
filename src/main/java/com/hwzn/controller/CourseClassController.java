package com.hwzn.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.Result;
import com.hwzn.pojo.dto.courseClass.AddCourseClassDto;
import com.hwzn.pojo.dto.courseClass.FilterCourseClassListDto;
import com.hwzn.pojo.entity.*;
import com.hwzn.service.CourseClassService;
import com.hwzn.service.CourseService;
import com.hwzn.service.CourseStudentService;
import com.hwzn.service.CourseTermService;
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
import java.util.Objects;

/**
 * @Author: hy
 * @Date: 2025/7/21 8:52
 * @Desc: 课程班级
 */
@RestController
@RequestMapping("/courseClass")
public class CourseClassController {

    @Resource
    CourseClassService courseClassService;

    @Resource
    CourseTermService courseTermService;

    @Resource
    CourseService courseService;

    @Resource
    CourseStudentService courseStudentService;

    //筛选课程班级列表
    @PostMapping("/filterCourseClassList")
    public Result filterCourseClassList(@Validated @RequestBody FilterCourseClassListDto filterCourseClassListDtot, HttpServletRequest request){
        String account = JWTUtil.getAccount(request.getHeader("token"));
        Integer role = JWTUtil.getRole(request.getHeader("token"));

        CourseEntity courseEntity = courseService.getCourseById(filterCourseClassListDtot.getCourseId());

        if(courseEntity==null){
            return Result.showInfo(2,"指定课程不存在",null);
        }
        CourseTermEntity courseTermEntity = courseTermService.getCourseTermById(filterCourseClassListDtot.getTermId());

        if(courseTermEntity==null){
            return Result.showInfo(2,"指定课程期次不存在",null);
        }

        if(role != 1 && !StrUtil.equals(courseEntity.getChargerAccount(), account) && (!courseEntity.getAssistants().contains(Objects.requireNonNull(account)) || !courseEntity.getAssistantAuthority().contains("student-management"))){
            return Result.showInfo(2,"仅限管理员和课程负责人及含有相关权限的助教可以操作",null);
        }
        IPage<CourseClassEntity> resultList = courseClassService.filterCourseClassList(filterCourseClassListDtot);
        return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
    }

    //添加课程班级
    @PostMapping("/addCourseClass")
    @Transactional
    public Result addCourseClass(@Validated @RequestBody AddCourseClassDto addCourseClassDto, HttpServletRequest request){
        //课程班级先删后插
        courseClassService.deleteCourseClassByDto(addCourseClassDto);
        CourseClassEntity courseClassEntity = new CourseClassEntity();
        BeanUtils.copyProperties(addCourseClassDto,courseClassEntity);
        courseClassService.addCourseClass(courseClassEntity);

        //课程学员先删后插
        courseStudentService.deleteCourseStudentByDto(addCourseClassDto);
        courseStudentService.addCourseStudentByDto(addCourseClassDto);

        JSONObject data = new JSONObject();
        data.set("id",courseClassEntity.getId());
        if(courseClassEntity.getId() == null){
            return Result.showInfo(5,"创建失败",null);
        }
        return Result.showInfo(0,"Success",data);
    }
}