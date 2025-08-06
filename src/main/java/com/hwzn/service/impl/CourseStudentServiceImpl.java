package com.hwzn.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.mapper.CourseStudentMapper;
import com.hwzn.pojo.dto.courseClass.AddCourseClassDto;
import com.hwzn.pojo.dto.courseStudent.FilterCourseStudentListDto;
import com.hwzn.pojo.entity.CourseStudentEntity;
import com.hwzn.service.CourseStudentService;
import com.hwzn.util.CommonUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @Author: hy
 * @Date: 2025/7/17 14:09
 * @Desc: 课程期次
 */
@Service
public class CourseStudentServiceImpl implements CourseStudentService {
	
	@Resource
	private CourseStudentMapper courseStudentMapper;

	@Override
	public IPage<CourseStudentEntity> filterCourseStudentList(FilterCourseStudentListDto filterCourseStudentListDto) {
		Page<CourseStudentEntity> page = new Page<>(filterCourseStudentListDto.getPageNum(),filterCourseStudentListDto.getPageSize());//分页
		QueryWrapper<CourseStudentEntity> queryWrapper = new QueryWrapper<>();
		CommonUtil.handleSortQuery(queryWrapper, filterCourseStudentListDto.getSortArray(), "course_students");
		queryWrapper.like(StrUtil.isNotBlank(filterCourseStudentListDto.getAccount()),"course_students.account",filterCourseStudentListDto.getAccount())
				.eq(filterCourseStudentListDto.getCourseId() !=null,"course_students.course_id",filterCourseStudentListDto.getCourseId())
				.eq(filterCourseStudentListDto.getTermId() !=null,"course_students.term_id",filterCourseStudentListDto.getTermId())
				.eq(filterCourseStudentListDto.getJoinMethod() != null,"course_students.join_method",filterCourseStudentListDto.getJoinMethod())
				.like(StrUtil.isNotBlank(filterCourseStudentListDto.getName()),"users.name",filterCourseStudentListDto.getName())
				.eq(StrUtil.isNotBlank(filterCourseStudentListDto.getClassCode()),"classes.code",filterCourseStudentListDto.getClassCode())
				.ge(StrUtil.isNotBlank(filterCourseStudentListDto.getStartTime()),"course_students.create_time",filterCourseStudentListDto.getStartTime())
				.le(StrUtil.isNotBlank(filterCourseStudentListDto.getEndTime()),"course_students.create_time",filterCourseStudentListDto.getEndTime());
		return courseStudentMapper.filterCourseStudentList(page,queryWrapper);
	}

	@Override
	public Integer addCourseStudent(CourseStudentEntity courseStudentEntity) {
		return courseStudentMapper.insert(courseStudentEntity);
	}

	@Override
	public Integer removeCourseStudent(Integer id) {
		return courseStudentMapper.deleteById(id);
	}

	@Override
	public CourseStudentEntity getCourseStudentById(Integer id) {
		return courseStudentMapper.selectById(id);
	}

	@Override
	public List<CourseStudentEntity> getCourseStudentByEntity(CourseStudentEntity courseStudentEntity) {
		return courseStudentMapper.getCourseStudentByEntity(courseStudentEntity);
	}

	@Override
	public List<CourseStudentEntity> checkCourseStudentByEntity(CourseStudentEntity courseStudentEntity) {
		return courseStudentMapper.checkCourseStudentByEntity(courseStudentEntity);
	}

	@Override
	public Integer addCourseStudentByDto(AddCourseClassDto addCourseClassDto) {
		return courseStudentMapper.addCourseStudentByDto(addCourseClassDto);
	}

	@Override
	public Integer deleteCourseStudentByDto(AddCourseClassDto addCourseClassDto) {
		return courseStudentMapper.deleteCourseStudentByDto(addCourseClassDto);
	}

	@Override
	public List<CourseStudentEntity> getCourseStudentForCurrent(CourseStudentEntity courseStudentEntity) {
		return courseStudentMapper.getCourseStudentForCurrent(courseStudentEntity);
	}
}