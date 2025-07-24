package com.hwzn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.courseClass.AddCourseClassDto;
import com.hwzn.pojo.dto.courseClass.FilterCourseClassListDto;
import com.hwzn.pojo.entity.CourseClassEntity;

/**
 * @Author: hy
 * @Date: 2025/7/18 17:26
 * @Desc: 课程学员
 */
public interface CourseClassService {

    IPage<CourseClassEntity> filterCourseClassList(FilterCourseClassListDto filterCourseClassListDto);

    Integer addCourseClass(CourseClassEntity courseClassEntity);

    Integer removeCourseClass(Integer id);

    IPage<CourseClassEntity> getCourseClassByEntity(CourseClassEntity courseClassEntity);

    Integer deleteCourseClassByDto(AddCourseClassDto addCourseClassDto);
}