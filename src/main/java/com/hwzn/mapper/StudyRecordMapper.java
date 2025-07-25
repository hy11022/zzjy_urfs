package com.hwzn.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.pojo.entity.CourseEntity;
import com.hwzn.pojo.entity.StudyRecordsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Author: hy
 * @Date: 2025/7/25 17:05
 * @Desc: 学习记录
 */
@SuppressWarnings("ALL")
@Mapper
@Repository
public interface StudyRecordMapper extends BaseMapper<StudyRecordsEntity> {

    @Select("SELECT study_records.*,users.name AS createrName,classes.name AS className FROM study_records " +
            " LEFT JOIN users ON study_records.creater_account = users.account" +
            " LEFT JOIN classes ON users.class_code = classes.code ${ew.customSqlSegment}")
    IPage<StudyRecordsEntity>  getCourseListByPage(Page<StudyRecordsEntity> page, @Param("ew") QueryWrapper<StudyRecordsEntity> queryWrapper);
}