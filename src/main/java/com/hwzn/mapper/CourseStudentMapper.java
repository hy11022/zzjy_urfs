package com.hwzn.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.pojo.dto.courseClass.AddCourseClassDto;
import com.hwzn.pojo.entity.CourseClassEntity;
import com.hwzn.pojo.entity.CourseStudentEntity;
import org.apache.ibatis.annotations.*;
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
public interface CourseStudentMapper extends BaseMapper<CourseStudentEntity> {

    @Select("SELECT course_students.*,classes.name AS className,users.name AS name FROM course_students " +
            " LEFT JOIN users ON course_students.account = users.account " +
            " LEFT JOIN classes ON users.class_code = classes.code ${ew.customSqlSegment}")
    IPage<CourseStudentEntity> filterCourseStudentList(Page<CourseStudentEntity> page, @Param("ew") QueryWrapper<CourseStudentEntity> queryWrapper);

    @Select("SELECT * FROM course_students " +
            " WHERE course_id=#{courseId}" +
            " AND term_id=#{termId}" +
            " AND account IN (SELECT account FROM users WHERE class_code = #{code})")
    List<CourseStudentEntity> getCourseStudentByEntity(CourseStudentEntity courseStudentEntity);

    @Select("SELECT * FROM course_students " +
            " WHERE course_id=#{courseId}" +
            " AND term_id=#{termId}" +
            " AND account = #{account}")
    List<CourseStudentEntity> checkCourseStudentByEntity(CourseStudentEntity courseStudentEntity);

    @Insert("INSERT INTO course_students (course_id,term_id,account)" +
            " SELECT #{courseId}, #{termId},account FROM users WHERE class_code=#{code};")
    Integer addCourseStudentByDto(AddCourseClassDto addCourseClassDto);

    @Delete("DELETE FROM course_students" +
            " WHERE course_id=#{courseId} AND term_id=#{termId} " +
            " AND account IN (SELECT account FROM users WHERE class_code=#{code})")
    Integer deleteCourseStudentByDto(AddCourseClassDto addCourseClassDto);

    @Select("SELECT * FROM course_students " +
            " WHERE course_id=#{courseId}" +
            " AND term_id=#{termId}" +
            " AND account =#{account}")
    List<CourseStudentEntity> getCourseStudentForCurrent(CourseStudentEntity courseStudentEntity);
}