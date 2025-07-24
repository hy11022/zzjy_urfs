package com.hwzn.controller;

import cn.hutool.core.util.StrUtil;
import com.hwzn.service.*;
import com.hwzn.pojo.Result;
import com.hwzn.util.JWTUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.json.JSONObject;
import javax.annotation.Resource;
import com.hwzn.pojo.dto.common.IdDto;
import com.hwzn.pojo.entity.CourseEntity;
import com.hwzn.pojo.dto.common.StatusDto;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import com.hwzn.pojo.entity.CourseMaterialsEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.hwzn.pojo.dto.courseMaterial.AddCourseMaterialDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;
import com.hwzn.pojo.dto.courseMaterial.UpdateCourseMaterialDto;
import com.hwzn.pojo.dto.courseMaterial.FilterCourseMaterialListDto;

/**
 * @Author: hy
 * @Date: 2025/7/222 11:26
 * @Desc: 课程资料
 */
@RestController
@RequestMapping("/courseMaterial")
public class CourseMaterialController {

    @Resource
    CourseMaterialService courseMaterialService;

    @Resource
    CourseService courseService;

    //筛选课程资料列表
    @PostMapping("/filterCourseMaterialList")
    public Result filterCourseMaterialList(@Validated @RequestBody FilterCourseMaterialListDto filterCourseMaterialListDto, HttpServletRequest request){
        String account = JWTUtil.getAccount(request.getHeader("token"));
        Integer role = JWTUtil.getRole(request.getHeader("token"));

        CourseEntity courseEntity = courseService.getCourseById(filterCourseMaterialListDto.getCourseId());
        if(courseEntity==null){
            return Result.showInfo(2,"指定课程不存在",null);
        }

//        if(role != 1 && !StrUtil.equals(courseEntity.getChargerAccount(), account) && (!courseEntity.getAssistants().contains(Objects.requireNonNull(account)) || !courseEntity.getAssistantAuthority().contains("student-management"))){
//            return Result.showInfo(2,"仅限管理员和课程负责人及含有相关权限的助教可以操作",null);
//        }
        IPage<CourseMaterialsEntity> resultList = courseMaterialService.filterCourseMaterialList(filterCourseMaterialListDto);
        return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
    }

    //创建课程资料
    @PostMapping("/createCourseMaterial")
    @Transactional
    public Result createCourseMaterial(@Validated @RequestBody AddCourseMaterialDto addCourseMaterialDto, HttpServletRequest request){
        String account = JWTUtil.getAccount(request.getHeader("token"));
        CourseMaterialsEntity courseMaterialsEntity = new CourseMaterialsEntity();
        BeanUtils.copyProperties(addCourseMaterialDto,courseMaterialsEntity);
        courseMaterialsEntity.setCreaterAccount(account);
        courseMaterialService.createCourseMaterial(courseMaterialsEntity);
        JSONObject data = new JSONObject();
        data.set("id",courseMaterialsEntity.getId());
        if(courseMaterialsEntity.getId() == null){
            return Result.showInfo(5,"创建失败",null);
        }
        return Result.showInfo(0,"Success",data);
    }

    //删除课程
    @PostMapping("/deleteCourseMaterial")
    @Transactional
    public Result deleteCourseMaterial(@Validated @RequestBody IdDto idDto){
        courseMaterialService.deleteCourseMaterial(idDto.getId());//删除课程
        return Result.showInfo(0,"Success", null);
    }

    //更新课程资料状态
    @PostMapping("/updateCourseMaterialStatus")
    @Transactional
    public Result updateCourseMaterialStatus(@Validated @RequestBody StatusDto statusDto, HttpServletRequest request){
        //先判断ID指定的数据是否存在
        CourseMaterialsEntity courseMaterialInfo = courseMaterialService.getCourseMaterialById(statusDto.getId());
        if(courseMaterialInfo == null){
            return Result.showInfo(2,"指定数据不存在",null);
        }
        //赋值
        CourseMaterialsEntity courseMaterialsEntity = new CourseMaterialsEntity();
        BeanUtils.copyProperties(statusDto,courseMaterialsEntity);
        //更新课程资料
        courseMaterialService.updateCourseMaterial(courseMaterialsEntity);
        return Result.showInfo(0,"Success",null);
    }

    //更新课程资料
    @PostMapping("/updateCourseMaterial")
    @Transactional
    public Result updateCourseMaterial(@Validated @RequestBody UpdateCourseMaterialDto updateCourseMaterialDto, HttpServletRequest request){
        String account = JWTUtil.getAccount(request.getHeader("token"));
        //先判断ID指定的数据是否存在
        CourseMaterialsEntity courseMaterialInfo = courseMaterialService.getCourseMaterialById(updateCourseMaterialDto.getId());
        if(courseMaterialInfo == null){
            return Result.showInfo(2,"指定数据不存在",null);
        }
        //赋值
        CourseMaterialsEntity courseMaterialsEntity = new CourseMaterialsEntity();
        BeanUtils.copyProperties(updateCourseMaterialDto,courseMaterialsEntity);
        courseMaterialsEntity.setCreaterAccount(account);
        //更新课程资料
        courseMaterialService.updateCourseMaterial(courseMaterialsEntity);
        return Result.showInfo(0,"Success",null);
    }
}