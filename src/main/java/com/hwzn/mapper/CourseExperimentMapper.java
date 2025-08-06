package com.hwzn.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.pojo.dto.common.IdDto;
import com.hwzn.pojo.entity.CourseExperimentEntity;
import com.hwzn.pojo.vo.DataVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.List;

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

    @Select("SELECT a.id,a.name,a.score_rate FROM course_experiments a" +
            " ${ew.customSqlSegment}")
    IPage<CourseExperimentEntity> filterCourseExperimentScoreRateList(Page<CourseExperimentEntity> page, @Param("ew") QueryWrapper<CourseExperimentEntity> queryWrapper);


    @Select("SELECT * FROM course_experiments WHERE course_id=#{courseId}" +
            " ORDER BY " +
            "    CASE " +
            "        WHEN experiment_start_time <= NOW() AND experiment_end_time >= NOW() THEN 1 " +
            "        WHEN experiment_start_time > NOW() THEN 2 " +
            "        WHEN experiment_end_time < NOW() THEN 3" +
            "    END," +
            "    CASE " +
            "        WHEN experiment_start_time <= NOW() AND experiment_end_time >= NOW() THEN experiment_start_time " +
            "        WHEN experiment_start_time > NOW() THEN experiment_start_time" +
            "        WHEN experiment_end_time < NOW() THEN experiment_start_time " +
            "    END;")
    List<CourseExperimentEntity> getExperimentInfoByCourseId(Integer courseId);

    @Select("SELECT a.id,course_id,a.name,a.cover,a.des,a.experiment_content_type," +
            " a.experiment_content, a.experiment_start_time,a.experiment_end_time,b.name AS courseName " +
            " FROM course_experiments a " +
            " LEFT JOIN courses b ON a.course_id=b.id" +
            " ${ew.customSqlSegment}")
    IPage<CourseExperimentEntity> filterMyTrainCourseExperimentList(Page<CourseExperimentEntity> page, @Param("ew") QueryWrapper<CourseExperimentEntity> queryWrapper);

    @Select("SELECT experimentResultCount,experimentTotalCount," +
            " passScore/experimentTotalCount*100 passRate, sumScore/experimentTotalCount averageScore," +
            " peopleAmount,experimentResultCount/peopleAmount averageExperimentCount" +
            " FROM(SELECT COUNT(1) experimentResultCount," +
            " SUM(CASE WHEN total_score IS NOT NULL THEN 1 ELSE 0 END) experimentTotalCount," +
            " SUM(CASE WHEN total_score>=60 THEN 1 ELSE 0 END) passScore," +
            " SUM(IFNULL(total_score,0)) sumScore,COUNT(DISTINCT creater_account) peopleAmount" +
            " FROM course_experiment_results WHERE type=1 AND experiment_id=#{id}) a;")
    DataVo fetchCourseExperimentDataAnalysis(IdDto idDto);
}