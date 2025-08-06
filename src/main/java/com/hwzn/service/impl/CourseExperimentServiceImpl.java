package com.hwzn.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.mapper.CourseExperimentMapper;
import com.hwzn.pojo.dto.common.IdDto;
import com.hwzn.pojo.dto.courseExperiment.FilterCourseExperimentListDto;
import com.hwzn.pojo.dto.courseExperiment.FilterCourseExperimentRateDto;
import com.hwzn.pojo.dto.courseExperiment.FilterMyTrainCourseExperimentListDto;
import com.hwzn.pojo.entity.CourseExperimentEntity;
import com.hwzn.pojo.vo.DataVo;
import com.hwzn.service.CourseExperimentService;
import com.hwzn.util.CommonUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: hy
 * @Date: 2025/7/24 11:00
 * @Desc: 课程实验
 */
@Service
public class CourseExperimentServiceImpl implements CourseExperimentService {
	
	@Resource
	private CourseExperimentMapper courseExperimentMapper;

	@Override
	public IPage<CourseExperimentEntity> filterCourseExperimentList(FilterCourseExperimentListDto filterCourseExperimentListDto) {
		Page<CourseExperimentEntity> page = new Page<>(filterCourseExperimentListDto.getPageNum(),filterCourseExperimentListDto.getPageSize());//分页
		QueryWrapper<CourseExperimentEntity> queryWrapper = new QueryWrapper<>();
		CommonUtil.handleSortQuery(queryWrapper, filterCourseExperimentListDto.getSortArray(), "a");
		queryWrapper.like(StrUtil.isNotBlank(filterCourseExperimentListDto.getName()),"a.name",filterCourseExperimentListDto.getName())
				.eq(filterCourseExperimentListDto.getCourseId() !=null,"a.course_id",filterCourseExperimentListDto.getCourseId());
		return courseExperimentMapper.filterCourseExperimentList(page,queryWrapper);
	}

	@Override
	public IPage<CourseExperimentEntity> filterCourseExperimentScoreRateList(FilterCourseExperimentRateDto filterCourseExperimentRateDto) {
		Page<CourseExperimentEntity> page = new Page<>(filterCourseExperimentRateDto.getPageNum(),filterCourseExperimentRateDto.getPageSize());//分页
		QueryWrapper<CourseExperimentEntity> queryWrapper = new QueryWrapper<>();
		CommonUtil.handleSortQuery(queryWrapper, filterCourseExperimentRateDto.getSortArray(), "a");
		queryWrapper.eq(filterCourseExperimentRateDto.getCourseId() !=null,"a.course_id",filterCourseExperimentRateDto.getCourseId());
		return courseExperimentMapper.filterCourseExperimentScoreRateList(page,queryWrapper);
	}

	@Override
	public CourseExperimentEntity getCourseExperimentInfoById(Integer id) {
		return courseExperimentMapper.selectById(id);
	}

	@Override
	public List<CourseExperimentEntity> getExperimentInfoByCourseId(Integer courseId) {
		return courseExperimentMapper.getExperimentInfoByCourseId(courseId);
	}

	@Override
	public Integer create(CourseExperimentEntity courseExperimentEntity) {
		return courseExperimentMapper.insert(courseExperimentEntity);
	}

	@Override
	public Integer updateCourseExperiment(CourseExperimentEntity courseExperimentEntity) {
		return courseExperimentMapper.updateById(courseExperimentEntity);
	}

	@Override
	public Integer deleteById(Integer id) {
		return courseExperimentMapper.deleteById(id);
	}

	@Override
	public IPage<CourseExperimentEntity> filterMyTrainCourseExperimentList(FilterMyTrainCourseExperimentListDto filterMyTrainCourseExperimentListDto) {
		Page<CourseExperimentEntity> page = new Page<>(filterMyTrainCourseExperimentListDto.getPageNum(),filterMyTrainCourseExperimentListDto.getPageSize());//分页
		QueryWrapper<CourseExperimentEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.like(StrUtil.isNotBlank(filterMyTrainCourseExperimentListDto.getName()),"a.name",filterMyTrainCourseExperimentListDto.getName())
				.le("a.experiment_start_time",DateTime.now())
				.ge("a.experiment_end_time", DateTime.now())
				.eq("a.allow_train",1)
				.apply("a.course_id IN (SELECT course_id FROM course_terms WHERE status =1)")
				.apply("a.course_id IN (SELECT course_id FROM course_students WHERE term_id IN (SELECT id FROM course_terms WHERE status=1) AND account='"+filterMyTrainCourseExperimentListDto.getAccount()+"') ")
				.apply("FIND_IN_SET('"+filterMyTrainCourseExperimentListDto.getClassCode()+"',a.experiment_class)");
		return courseExperimentMapper.filterMyTrainCourseExperimentList(page,queryWrapper);
	}

	@Override
	public DataVo fetchCourseExperimentDataAnalysis(IdDto idDto) {
		return courseExperimentMapper.fetchCourseExperimentDataAnalysis(idDto);
	}
}