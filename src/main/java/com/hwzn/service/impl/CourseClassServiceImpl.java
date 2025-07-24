package com.hwzn.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.mapper.CourseClassMapper;
import com.hwzn.pojo.dto.courseClass.AddCourseClassDto;
import com.hwzn.pojo.dto.courseClass.FilterCourseClassListDto;
import com.hwzn.pojo.entity.CourseClassEntity;
import com.hwzn.service.CourseClassService;
import com.hwzn.util.CommonUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: hy
 * @Date: 2025/7/17 14:09
 * @Desc: 课程班级
 */
@Service
public class CourseClassServiceImpl implements CourseClassService {
	
	@Resource
	private CourseClassMapper courseClassMapper;

	@Override
	public IPage<CourseClassEntity> filterCourseClassList(FilterCourseClassListDto filterCourseClassListDto) {
		Page<CourseClassEntity> page = new Page<>(filterCourseClassListDto.getPageNum(),filterCourseClassListDto.getPageSize());//分页
		QueryWrapper<CourseClassEntity> queryWrapper = new QueryWrapper<>();
		CommonUtil.handleSortQuery(queryWrapper, filterCourseClassListDto.getSortArray(), "course_classes");
		queryWrapper.like(StrUtil.isNotBlank(filterCourseClassListDto.getName()),"course_classes.name",filterCourseClassListDto.getName())
				.eq(filterCourseClassListDto.getCourseId() !=null,"course_classes.course_id",filterCourseClassListDto.getCourseId())
				.eq(filterCourseClassListDto.getTermId() !=null,"course_classes.term_id",filterCourseClassListDto.getTermId())
				.eq(StrUtil.isNotBlank(filterCourseClassListDto.getChargerAccount()),"course_classes.charger_account",filterCourseClassListDto.getChargerAccount())
				.ge(StrUtil.isNotBlank(filterCourseClassListDto.getStartTime()),"course_classes.create_time",filterCourseClassListDto.getStartTime())
				.le(StrUtil.isNotBlank(filterCourseClassListDto.getEndTime()),"course_classes.create_time",filterCourseClassListDto.getEndTime());
		return courseClassMapper.filterCourseClassList(page,queryWrapper);
	}

	@Override
	public Integer addCourseClass(CourseClassEntity courseClassEntity) {
		return courseClassMapper.insert(courseClassEntity);
	}

	@Override
	public Integer removeCourseClass(Integer id) {
		return courseClassMapper.deleteById(id);
	}

	@Override
	public IPage<CourseClassEntity> getCourseClassByEntity(CourseClassEntity courseClassEntity) {
		Page<CourseClassEntity> page = new Page<>(1,10);//分页
		QueryWrapper<CourseClassEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(courseClassEntity.getCourseId() !=null,"course_classes.course_id",courseClassEntity.getCourseId())
				.eq(courseClassEntity.getTermId() !=null,"course_classes.term_id",courseClassEntity.getTermId())
				.eq(StrUtil.isNotBlank(courseClassEntity.getCode()),"course_classes.code",courseClassEntity.getCode());
		return courseClassMapper.selectPage(page,queryWrapper);
	}

	@Override
	public Integer deleteCourseClassByDto(AddCourseClassDto addCourseClassDto) {
		Map map =new HashMap<>();
		map.put("course_id",addCourseClassDto.getCourseId());
		map.put("term_id",addCourseClassDto.getTermId());
		map.put("code",addCourseClassDto.getCode());
		return courseClassMapper.deleteByMap(map);
	}
}