package com.hwzn.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.pojo.entity.CourseClassEntity;
import com.hwzn.pojo.entity.CourseExperimentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Author: hy
 * @Date: 2025/7/24 11:02
 * @Desc: 课程实验
 */
@SuppressWarnings("ALL")
@Mapper
@Repository
public interface CourseExperimentMapper extends BaseMapper<CourseExperimentEntity> {

    @Select("SELECT a.id,course_id,a.name,a.cover,a.des,a.experiment_content_type," +
            " a.experiment_content, a.experiment_start_time,a.experiment_end_time FROM course_experiments a" +
            " ${ew.customSqlSegment}")
    IPage<CourseExperimentEntity> filterCourseExperimentList(Page<CourseExperimentEntity> page, @Param("ew") QueryWrapper<CourseExperimentEntity> queryWrapper);

}