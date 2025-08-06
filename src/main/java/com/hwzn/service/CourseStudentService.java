package com.hwzn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.courseClass.AddCourseClassDto;
import com.hwzn.pojo.dto.courseStudent.FilterCourseStudentListDto;
import com.hwzn.pojo.entity.CourseClassEntity;
import com.hwzn.pojo.entity.CourseStudentEntity;
import java.util.List;

/**
 * @Author: hy
 * @Date: 2025/7/18 17:26
 * @Desc: 课程学员
 */
public interface CourseStudentService {

    IPage<CourseStudentEntity> filterCourseStudentList(FilterCourseStudentListDto filterCourseStudentListDto);

    Integer addCourseStudent(CourseStudentEntity courseStudentEntity);

    Integer removeCourseStudent(Integer id);

    CourseStudentEntity getCourseStudentById(Integer id);

    List<CourseStudentEntity> getCourseStudentByEntity(CourseStudentEntity courseStudentEntity);

    List<CourseStudentEntity> checkCourseStudentByEntity(CourseStudentEntity courseStudentEntity);

    Integer addCourseStudentByDto(AddCourseClassDto addCourseClassDto);

    Integer deleteCourseStudentByDto(AddCourseClassDto addCourseClassDto);

    List<CourseStudentEntity> getCourseStudentForCurrent(CourseStudentEntity courseStudentEntity);
}