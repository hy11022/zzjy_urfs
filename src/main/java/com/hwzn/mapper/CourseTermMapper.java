package com.hwzn.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.hwzn.pojo.entity.CourseTermEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: hy
 * @Date: 2025/7/17 14:10
 * @Desc: 课程期次
 */
@SuppressWarnings("ALL")
@Mapper
@Repository
public interface CourseTermMapper extends BaseMapper<CourseTermEntity> {

    @Select("SELECT * FROM course_terms " +
            "LEFT JOIN courses ON course_terms.course_id = courses.id " +
            "${ew.customSqlSegment} ")
    IPage<CourseTermEntity> filterCourseTermList(Page<CourseTermEntity> page, @Param("ew") QueryWrapper<CourseTermEntity> queryWrapper);
}