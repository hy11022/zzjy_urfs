package com.hwzn.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.mapper.CourseMapper;
import com.hwzn.pojo.dto.course.*;
import com.hwzn.pojo.entity.CourseEntity;
import com.hwzn.service.CourseService;
import com.hwzn.util.CommonUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/26 15:36
 * @Desc: 课程
 */
@Service
public class CourseServiceImpl implements CourseService {
	
	@Resource
	private CourseMapper courseMapper;

	@Override
	public IPage<CourseEntity> getCourseListByPage(GetCourseListByPageDto getCourseListByPageDto) {
		Page<CourseEntity> page = new Page<>(getCourseListByPageDto.getPageNum(),getCourseListByPageDto.getPageSize());//分页
		QueryWrapper<CourseEntity> queryWrapper = new QueryWrapper<>();
		CommonUtil.handleSortQuery(queryWrapper, getCourseListByPageDto.getSortArray(), "courses");
		queryWrapper.like(StrUtil.isNotBlank(getCourseListByPageDto.getName()),"courses.name",getCourseListByPageDto.getName())
				.like(StrUtil.isNotBlank(getCourseListByPageDto.getAuthor()),"courses.author",getCourseListByPageDto.getAuthor())
				.like(StrUtil.isNotBlank(getCourseListByPageDto.getOrganization()),"courses.organization",getCourseListByPageDto.getOrganization())
				.like(StrUtil.isNotBlank(getCourseListByPageDto.getDes()),"courses.des",getCourseListByPageDto.getDes())
				.eq(StrUtil.isNotBlank(getCourseListByPageDto.getChargerAccount()),"courses.charger_account",getCourseListByPageDto.getChargerAccount());
		return courseMapper.getCourseListByPage(page,queryWrapper);
	}

	@Override
	public IPage<CourseEntity> filterMyChargingCourseList(FilterMyChargingCourseListDto filterMyChargingCourseListDto) {
		Page<CourseEntity> page = new Page<>(filterMyChargingCourseListDto.getPageNum(),filterMyChargingCourseListDto.getPageSize());//分页
		QueryWrapper<CourseEntity> queryWrapper = new QueryWrapper<>();
		CommonUtil.handleSortQuery(queryWrapper, filterMyChargingCourseListDto.getSortArray(), "courses");
		queryWrapper.like(StrUtil.isNotBlank(filterMyChargingCourseListDto.getName()),"courses.name",filterMyChargingCourseListDto.getName())
				.like(StrUtil.isNotBlank(filterMyChargingCourseListDto.getDes()),"courses.des",filterMyChargingCourseListDto.getDes())
				.eq("courses.charger_account",filterMyChargingCourseListDto.getAccount());
		return courseMapper.getCourseListByPage(page,queryWrapper);
	}

	@Override
	public void createCourse(CourseEntity courseEntity) {
		courseMapper.insert(courseEntity);
	}

	@Override
	public IPage<CourseEntity> filterMyManagingCourseList(GetCourseListByPageDto getCourseListByPageDto) {
		Page<CourseEntity> page = new Page<>(getCourseListByPageDto.getPageNum(),getCourseListByPageDto.getPageSize());//分页
		QueryWrapper<CourseEntity> queryWrapper = new QueryWrapper<>();
		CommonUtil.handleSortQuery(queryWrapper, getCourseListByPageDto.getSortArray(), "courses");
		queryWrapper.like(StrUtil.isNotBlank(getCourseListByPageDto.getName()),"courses.name",getCourseListByPageDto.getName())
				.like(StrUtil.isNotBlank(getCourseListByPageDto.getAuthor()),"courses.author",getCourseListByPageDto.getAuthor())
				.like(StrUtil.isNotBlank(getCourseListByPageDto.getOrganization()),"courses.organization",getCourseListByPageDto.getOrganization())
				.like(StrUtil.isNotBlank(getCourseListByPageDto.getDes()),"courses.des",getCourseListByPageDto.getDes())
				.eq(StrUtil.isNotBlank(getCourseListByPageDto.getChargerAccount()),"courses.charger_account",getCourseListByPageDto.getChargerAccount())
				.eq("courses.charger_account", getCourseListByPageDto.getAccount())
				.or()
				.apply("FIND_IN_SET('"+getCourseListByPageDto.getAccount()+"',courses.assistants)");
		return courseMapper.filterMyManagingCourseList(page,queryWrapper,getCourseListByPageDto.getAccount());
	}

	@Override
	public CourseEntity getCourseById(Integer id) {
		return courseMapper.selectById(id);
	}

	@Override
	public CourseEntity getCourseInfoById(Integer id) {
		return courseMapper.getCourseInfoById(id);
	}

	@Override
	public CourseEntity getCourseByTermId(Integer id) {
		return courseMapper.getCourseByTermId(id);
	}

	@Override
	public CourseEntity getCourseByCourseExperimentId(Integer id) {
		return courseMapper.getCourseByCourseExperimentId(id);
	}

	@Override
	public void updateCourse(CourseEntity courseEntity) {
		courseMapper.updateById(courseEntity);
	}

	@Override
	public void deleteCourse(Integer id) {
		courseMapper.deleteById(id);
	}

	@Override
	public IPage<CourseEntity> filterMyJoinedCourseList(FilterMyJoinedCourseListDto filterMyJoinedCourseListDto) {
		Page<CourseEntity> page = new Page<>(filterMyJoinedCourseListDto.getPageNum(),filterMyJoinedCourseListDto.getPageSize());//分页
		QueryWrapper<CourseEntity> queryWrapper = new QueryWrapper<>();
		CommonUtil.handleSortQuery(queryWrapper, filterMyJoinedCourseListDto.getSortArray(), "courses");
		queryWrapper.like(StrUtil.isNotBlank(filterMyJoinedCourseListDto.getName()),"courses.name",filterMyJoinedCourseListDto.getName())
				.like(StrUtil.isNotBlank(filterMyJoinedCourseListDto.getAuthor()),"courses.author",filterMyJoinedCourseListDto.getAuthor())
				.like(StrUtil.isNotBlank(filterMyJoinedCourseListDto.getOrganization()),"courses.organization",filterMyJoinedCourseListDto.getOrganization())
				.apply("courses.id IN (SELECT course_id FROM course_students WHERE account ='"+filterMyJoinedCourseListDto.getAccount()+"')");
		return courseMapper.filterMyJoinedCourseList(page,queryWrapper);
	}
}