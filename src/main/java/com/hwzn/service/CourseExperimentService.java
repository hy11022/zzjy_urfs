package com.hwzn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.courseExperiment.FilterCourseExperimentListDto;
import com.hwzn.pojo.dto.courseExperiment.FilterCourseExperimentRateDto;
import com.hwzn.pojo.entity.CourseExperimentEntity;
import java.util.List;

/**
 * @Author: hy
 * @Date: 2025/7/24 10:58
 * @Desc: 课程实验
 */
public interface CourseExperimentService {

    IPage<CourseExperimentEntity> filterCourseExperimentList(FilterCourseExperimentListDto filterCourseExperimentListDto);

    IPage<CourseExperimentEntity> filterCourseExperimentScoreRateList(FilterCourseExperimentRateDto filterCourseExperimentRateDto);

    CourseExperimentEntity getCourseExperimentInfoById(Integer id);

    List<CourseExperimentEntity> getExperimentInfoByCourseId(Integer courseId);

    Integer create(CourseExperimentEntity courseExperimentEntity);

    Integer updateCourseExperiment(CourseExperimentEntity courseExperimentEntity);

    Integer deleteById(Integer id);
}