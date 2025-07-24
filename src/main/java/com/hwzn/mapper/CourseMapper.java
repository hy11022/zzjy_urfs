package com.hwzn.mapper;

import com.hwzn.pojo.entity.CourseEntity;
import com.hwzn.pojo.vo.CourseInfoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @Author: hy
 * @Date: 2025/7/18 15:38
 * @Desc: 课程
 */
@SuppressWarnings("ALL")
@Mapper
@Repository
public interface CourseMapper extends BaseMapper<CourseEntity> {

    @Select("SELECT courses.id,courses.name,courses.author,courses.organization,courses.des," +
            " courses.charger_account,courses.cover,courses.assistants,courses.assistant_authority," +
            " users.name AS chargerName" +
            " FROM courses LEFT JOIN users ON courses.charger_account =users.account" +
            " ${ew.customSqlSegment} ")
    IPage<CourseEntity> getCourseListByPage(Page<CourseEntity> page, @Param("ew") QueryWrapper<CourseEntity> queryWrapper);

    @Select("SELECT courses.id,courses.name,courses.author,courses.organization,courses.des," +
            " courses.charger_account,courses.cover,courses.assistants,courses.assistant_authority," +
            " users.name AS chargerName,CASE WHEN courses.charger_account <> #{account} THEN true ELSE false END isAssistant" +
            " FROM courses LEFT JOIN users ON courses.charger_account =users.account" +
            " ${ew.customSqlSegment} ")
    IPage<CourseEntity> filterMyManagingCourseList(Page<CourseEntity> page, @Param("ew") QueryWrapper<CourseEntity> queryWrapper,String account);

    @Select("SELECT * FROM courses LEFT JOIN course_terms ON courses.id = course_terms.course_id" +
            " WHERE course_terms.id=#{id}")
    CourseEntity getCourseByTermId(Integer id);

    @Select("SELECT * FROM courses LEFT JOIN course_experiments ON courses.id = course_experiments.course_id" +
            " WHERE course_experiments.id=#{id}")
    CourseEntity getCourseByCourseExperimentId(Integer id);

    @Select("SELECT courses.*,users.name AS chargerName FROM courses LEFT JOIN users ON courses.charger_account = users.account" +
            " WHERE courses.id=#{id}")
    CourseEntity getCourseInfoById(Integer id);

    @Select("SELECT courses.id,courses.name,courses.author,courses.organization,courses.cover " +
            " FROM courses ${ew.customSqlSegment}")
    IPage<CourseEntity> filterMyJoinedCourseList(Page<CourseEntity> page, @Param("ew") QueryWrapper<CourseEntity> queryWrapper);

}