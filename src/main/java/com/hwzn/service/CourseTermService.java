package com.hwzn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.courseTerm.CreateCourseTermDto;
import com.hwzn.pojo.dto.courseTerm.FilterCourseTermListDto;
import com.hwzn.pojo.entity.CourseTermEntity;

import java.util.List;

/**
 * @Author: hy
 * @Date: 2025/7/17 14:06
 * @Desc: 课程期次接口
 */
public interface CourseTermService {

    IPage<CourseTermEntity> filterCourseTermList(FilterCourseTermListDto filterCourseTermListDto);

    Integer createCourseTerm(CourseTermEntity courseTermEntity);

    IPage<CourseTermEntity> getCourseTermListByTime(Integer courseId,String startTime,String endTime,Integer id);

    Integer updateCourseTerm(CourseTermEntity courseTermEntity);

    CourseTermEntity getCourseTermById(Integer id);

    Integer deleteCourseTerm(Integer id);

    List<CourseTermEntity> getCurrentTermInfo(Integer courseId);
}