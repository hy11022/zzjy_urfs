package com.hwzn.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.mapper.CourseTermMapper;
import com.hwzn.pojo.dto.courseTerm.FilterCourseTermListDto;
import com.hwzn.pojo.entity.CourseTermEntity;
import com.hwzn.service.CourseTermService;
import com.hwzn.util.CommonUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: hy
 * @Date: 2025/7/17 14:09
 * @Desc: 课程期次
 */
@Service
public class CourseTermServiceImpl implements CourseTermService {
	
	@Resource
	private CourseTermMapper courseTermMapper;

	@Override
	public IPage<CourseTermEntity> filterCourseTermList(FilterCourseTermListDto filterCourseTermListDto) {
		Page<CourseTermEntity> page = new Page<>(filterCourseTermListDto.getPageNum(),filterCourseTermListDto.getPageSize());//分页
		QueryWrapper<CourseTermEntity> queryWrapper = new QueryWrapper<>();
		CommonUtil.handleSortQuery(queryWrapper, filterCourseTermListDto.getSortArray(), "course_terms");
		queryWrapper.like(StrUtil.isNotBlank(filterCourseTermListDto.getName()),"course_terms.name",filterCourseTermListDto.getName())
				.eq(filterCourseTermListDto.getCourseId() !=null,"course_terms.course_id",filterCourseTermListDto.getCourseId())
				.eq(filterCourseTermListDto.getStatus() != null,"courses.charger_account",filterCourseTermListDto.getStatus())
				.ge(StrUtil.isNotBlank(filterCourseTermListDto.getStartTime()),"course_terms.end_time",filterCourseTermListDto.getStartTime())
				.le(StrUtil.isNotBlank(filterCourseTermListDto.getEndTime()),"course_terms.end_time",filterCourseTermListDto.getEndTime());
		return courseTermMapper.filterCourseTermList(page,queryWrapper);
	}

	@Override
	public Integer createCourseTerm(CourseTermEntity courseTermEntity) {
		return courseTermMapper.insert(courseTermEntity);
	}

	@Override
	public IPage<CourseTermEntity> getCourseTermListByTime(Integer courseId, String startTime,String endTime, Integer id) {
		Page<CourseTermEntity> page = new Page<>(1,999);//分页
		QueryWrapper<CourseTermEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("course_id",courseId)
				.and(wq -> wq.le("start_time",startTime)
				.ge("end_time",endTime)
				.or()
				.le("start_time",startTime)
				.ge("end_time",endTime)
				.or()
				.between("start_time",startTime,endTime)
				.or()
				.between("end_time",startTime,endTime))
				.notIn(id != null,"id",id);//去掉自身
		return courseTermMapper.selectPage(page,queryWrapper);
	}

	@Override
	public Integer updateCourseTerm(CourseTermEntity courseTermEntity) {
		return courseTermMapper.updateById(courseTermEntity);
	}

	@Override
	public CourseTermEntity getCourseTermById(Integer id) {
		return courseTermMapper.selectById(id);
	}

	@Override
	public Integer deleteCourseTerm(Integer id) {
		return courseTermMapper.deleteById(id);
	}

	@Override
	public List<CourseTermEntity> getCurrentTermInfo(Integer courseId) {
		Map map = new HashMap<>();
		map.put("status",1);
		map.put("course_id",courseId);
		return courseTermMapper.selectByMap(map);
	}

	@Override
	public List<CourseTermEntity> getCurrentTermInfoByCourseId(Integer courseId) {
		return courseTermMapper.getCurrentTermInfoByCourseId(courseId);
	}

	@Override
	public List<CourseTermEntity> getCourseTermListOnTime() {
		return courseTermMapper.getCourseTermListOnTime();
	}
}