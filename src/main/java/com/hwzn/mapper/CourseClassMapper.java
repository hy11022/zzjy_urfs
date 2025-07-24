package com.hwzn.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.pojo.entity.CourseClassEntity;
import com.hwzn.pojo.entity.CourseStudentEntity;
import com.hwzn.pojo.entity.CourseTermEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Author: hy
 * @Date: 2025/7/18 17:29
 * @Desc: 课程班级
 */
@SuppressWarnings("ALL")
@Mapper
@Repository
public interface CourseClassMapper extends BaseMapper<CourseClassEntity> {

    @Select("SELECT course_classes.*,users.name AS chargerName,classes.name AS name FROM course_classes " +
            " LEFT JOIN users ON course_classes.charger_account = users.account " +
            " LEFT JOIN classes ON course_classes.code = classes.code ${ew.customSqlSegment}")
    IPage<CourseClassEntity> filterCourseClassList(Page<CourseClassEntity> page, @Param("ew") QueryWrapper<CourseClassEntity> queryWrapper);

}