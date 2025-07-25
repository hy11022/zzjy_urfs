package com.hwzn.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.mapper.CourseExperimentMapper;
import com.hwzn.pojo.dto.courseExperiment.FilterCourseExperimentListDto;
import com.hwzn.pojo.dto.courseExperiment.FilterCourseExperimentRateDto;
import com.hwzn.pojo.entity.CourseExperimentEntity;
import com.hwzn.service.CourseExperimentService;
import com.hwzn.util.CommonUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//				.eq(filterCourseExperimentListDto.getAllowTrain() !=null,"a.allow_train",filterCourseExperimentListDto.getAllowTrain())
//				.eq(StrUtil.isNotBlank(filterCourseExperimentListDto.getProjectName()),"a.project_name",filterCourseExperimentListDto.getProjectName());
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
		Map map = new HashMap<>();
		map.put("course_id",courseId);
		return courseExperimentMapper.selectByMap(map);
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
}