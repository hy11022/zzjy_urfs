package com.hwzn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.course.*;
import com.hwzn.pojo.entity.CourseEntity;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/26 15:35
 * @Desc: 课程接口
 */
public interface CourseService {

    IPage<CourseEntity> getCourseListByPage(GetCourseListByPageDto getCourseListByPageDto);

    IPage<CourseEntity> filterMyChargingCourseList(FilterMyChargingCourseListDto filterMyChargingCourseListDto);

    void createCourse(CourseEntity courseEntity);

    IPage<CourseEntity> filterMyManagingCourseList(GetCourseListByPageDto getCourseListByPageDto);


    CourseEntity getCourseById(Integer id);

    CourseEntity getCourseInfoById(Integer id);

    CourseEntity getCourseByTermId(Integer id);

    CourseEntity getCourseByCourseExperimentId(Integer id);

    void updateCourse(CourseEntity courseEntity);

    void deleteCourse(Integer id);

    IPage<CourseEntity> filterMyJoinedCourseList(FilterMyJoinedCourseListDto filterMyJoinedCourseListDto);

}