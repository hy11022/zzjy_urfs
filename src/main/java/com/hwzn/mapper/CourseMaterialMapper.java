package com.hwzn.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.pojo.entity.CourseClassEntity;
import com.hwzn.pojo.entity.CourseMaterialsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hy
 * @Date: 2025/7/18 17:29
 * @Desc: 课程学员
 */
@SuppressWarnings("ALL")
@Mapper
@Repository
public interface CourseMaterialMapper extends BaseMapper<CourseMaterialsEntity> {

    @Select("SELECT course_materials.*,users.name AS createrName FROM course_materials " +
            " LEFT JOIN users ON course_materials.creater_account = users.account ${ew.customSqlSegment}")
    IPage<CourseMaterialsEntity> filterCourseMaterialList(Page<CourseMaterialsEntity> page, @Param("ew") QueryWrapper<CourseMaterialsEntity> queryWrapper);

    @Select("SELECT course_materials.*,users.name AS createrName FROM course_materials " +
            " LEFT JOIN users ON course_materials.creater_account = users.account" +
            " WHERE course_materials.course_id=#{courseId} AND course_materials.status=1 " +
            " ORDER BY course_materials.create_time DESC")
    List<CourseMaterialsEntity> getCourseMaterialByCourse(Integer courseId);
}