package com.hwzn.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.hwzn.pojo.Result;
import cn.hutool.json.JSONUtil;
import com.hwzn.pojo.dto.common.IdDto;
import com.hwzn.pojo.dto.courseTerm.CreateCourseTermDto;
import com.hwzn.pojo.dto.courseTerm.FilterCourseTermListDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.courseTerm.UpdateCourseTermDto;
import com.hwzn.pojo.entity.CourseEntity;
import com.hwzn.pojo.entity.CourseTermEntity;
import com.hwzn.service.CourseService;
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
import cn.hutool.core.date.DateUtil;
import java.util.Objects;

/**
 * @Author: hy
 * @Date: 2025/7/17 13:27
 * @Desc: 课程期次
 */
@RestController
@RequestMapping("/courseTerm")
public class CourseTermController {

    @Resource
    CourseTermService courseTermService;

    @Resource
    CourseService courseService;

    //筛选课程期次列表
    @PostMapping("/filterCourseTermList")
    public Result filterCourseTermList(@Validated @RequestBody FilterCourseTermListDto filterCourseTermListDto, HttpServletRequest request){
        String account = JWTUtil.getAccount(request.getHeader("token"));
        Integer role = JWTUtil.getRole(request.getHeader("token"));

        CourseEntity courseEntity = courseService.getCourseById(filterCourseTermListDto.getCourseId());

        if(role != 1 && !StrUtil.equals(courseEntity.getChargerAccount(), account) && ( !courseEntity.getAssistants().contains(Objects.requireNonNull(account)) || !courseEntity.getAssistantAuthority().contains("course-term"))){
            return Result.showInfo(2,"仅限管理员和课程负责人及含有相关权限的助教可以操作",null);
        }
        IPage<CourseTermEntity> resultList = courseTermService.filterCourseTermList(filterCourseTermListDto);
        return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
    }

    //创建课程期次
    @PostMapping("/createCourseTerm")
    @Transactional
    public Result createCourseTerm(@Validated @RequestBody CreateCourseTermDto createCourseTermDto, HttpServletRequest request){
        String account = JWTUtil.getAccount(request.getHeader("token"));
        Integer role = JWTUtil.getRole(request.getHeader("token"));

        CourseEntity courseEntity = courseService.getCourseById(createCourseTermDto.getCourseId());

        if(role != 1 && !StrUtil.equals(courseEntity.getChargerAccount(), account) && ( !courseEntity.getAssistants().contains(Objects.requireNonNull(account)) || !courseEntity.getAssistantAuthority().contains("course-term"))){
            return Result.showInfo(2,"仅限管理员和课程负责人及含有相关权限的助教可以操作",null);
        }
        DateTime startTime = DateUtil.parseDateTime(createCourseTermDto.getStartTime());
        DateTime endTime = DateUtil.parseDateTime(createCourseTermDto.getEndTime());
        if(startTime.after(endTime)){
            return Result.showInfo(3,"开始时间要小于结束时间",null);
        }
        if(DateTime.now().after(endTime)){
            return Result.showInfo(3,"结束时间必须在当前时间之后",null);
        }
        IPage<CourseTermEntity> startTermList = courseTermService.getCourseTermListByTime(createCourseTermDto.getCourseId(),createCourseTermDto.getStartTime(),createCourseTermDto.getEndTime(),null);
        if(!startTermList.getRecords().isEmpty()){
            return Result.showInfo(4,"时间不可与该课程其他期次时间范围重叠",null);
        }

        CourseTermEntity courseTermEntity = new CourseTermEntity();
        BeanUtils.copyProperties(createCourseTermDto,courseTermEntity);
        if(DateTime.now().after(startTime)){
            courseTermEntity.setStatus(1);
        }
        //创建课程期次
        courseTermService.createCourseTerm(courseTermEntity);
        JSONObject data = new JSONObject();
        data.set("id",courseEntity.getId());
        if(courseEntity.getId() == null){
            return Result.showInfo(5,"创建失败",null);
        }
        return Result.showInfo(0,"Success",data);
    }

    //更新课程期次
    @PostMapping("/updateCourseTerm")
    @Transactional
    public Result updateCourseTerm(@Validated @RequestBody UpdateCourseTermDto updateCourseTermDto, HttpServletRequest request){
        String account = JWTUtil.getAccount(request.getHeader("token"));
        Integer role = JWTUtil.getRole(request.getHeader("token"));

        CourseEntity courseEntity = courseService.getCourseByTermId(updateCourseTermDto.getId());

        if(role != 1 && !StrUtil.equals(courseEntity.getChargerAccount(), account) && ( !courseEntity.getAssistants().contains(Objects.requireNonNull(account)) || !courseEntity.getAssistantAuthority().contains("course-term"))){
            return Result.showInfo(2,"仅限管理员和课程负责人及含有相关权限的助教可以操作",null);
        }
        DateTime startTime = DateUtil.parseDateTime(updateCourseTermDto.getStartTime());
        DateTime endTime = DateUtil.parseDateTime(updateCourseTermDto.getEndTime());
        if(startTime.after(endTime)){
            return Result.showInfo(3,"开始时间要小于结束时间",null);
        }

        CourseTermEntity courseTermInfo = courseTermService.getCourseTermById(updateCourseTermDto.getId());

        if(courseTermInfo.getStatus()==3){
            if(!Objects.equals(courseTermInfo.getStartTime(), updateCourseTermDto.getStartTime()) || !Objects.equals(courseTermInfo.getEndTime(), updateCourseTermDto.getEndTime())){
                return Result.showInfo(3,"已结束的期次不可更改时间",null);
            }
        }

        if(courseTermInfo.getStatus()==1 && !Objects.equals(courseTermInfo.getStartTime(), updateCourseTermDto.getStartTime())){
            return Result.showInfo(3,"进行中的期次不可更新开始时间",null);
        }

        if(courseTermInfo.getStatus()==1 && DateTime.now().after(endTime)){
            return Result.showInfo(3,"更新的结束时间应为当前时间之后",null);
        }

        IPage<CourseTermEntity> startTermList = courseTermService.getCourseTermListByTime(courseEntity.getId(),updateCourseTermDto.getStartTime(),updateCourseTermDto.getEndTime(),updateCourseTermDto.getId());
        if(!startTermList.getRecords().isEmpty()){
            return Result.showInfo(4,"时间不可与该课程其他期次时间范围重叠",null);
        }
        //赋值
        CourseTermEntity courseTermEntity = new CourseTermEntity();
        BeanUtils.copyProperties(updateCourseTermDto,courseTermEntity);
        //更新课程期次
        courseTermService.updateCourseTerm(courseTermEntity);
        return Result.showInfo(0,"Success",null);
    }

    //删除课程期次
    @PostMapping("/deleteCourseTerm")
    @Transactional
    public Result deleteCourseTerm(@Validated @RequestBody IdDto idDto, HttpServletRequest request){
        String account = JWTUtil.getAccount(request.getHeader("token"));
        Integer role = JWTUtil.getRole(request.getHeader("token"));

        CourseEntity courseEntity = courseService.getCourseByTermId(idDto.getId());

        if(role != 1 && !StrUtil.equals(courseEntity.getChargerAccount(), account) && ( !courseEntity.getAssistants().contains(Objects.requireNonNull(account)) || !courseEntity.getAssistantAuthority().contains("course-term"))){
            return Result.showInfo(2,"仅限管理员和课程负责人及含有相关权限的助教可以操作",null);
        }

        //先判断ID指定的数据是否存在
        CourseTermEntity courseTermEntity = courseTermService.getCourseTermById(idDto.getId());
        if(courseTermEntity == null){
            return Result.showInfo(2,"指定数据不存在",null);
        }
        if(courseTermEntity.getStatus()==1 || courseTermEntity.getStatus()==3){
            return Result.showInfo(3,"进行中和已结束的期次不可删除",null);
        }
        courseTermService.deleteCourseTerm(idDto.getId());//删除课程
        return Result.showInfo(0,"Success", null);
    }
}