package com.hwzn.service.impl;

import com.hwzn.pojo.dto.courseMaterial.FilterCourseMaterialListDto;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.entity.CourseMaterialsEntity;
import com.hwzn.service.CourseMaterialService;
import org.springframework.stereotype.Service;
import com.hwzn.mapper.CourseMaterialMapper;
import cn.hutool.core.util.StrUtil;
import javax.annotation.Resource;
import com.hwzn.util.CommonUtil;

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
public class CourseMaterialServiceImpl implements CourseMaterialService {
	
	@Resource
	private CourseMaterialMapper courseMaterialMapper;

	@Override
	public IPage<CourseMaterialsEntity> filterCourseMaterialList(FilterCourseMaterialListDto filterCourseMaterialListDto) {
		Page<CourseMaterialsEntity> page = new Page<>(filterCourseMaterialListDto.getPageNum(),filterCourseMaterialListDto.getPageSize());//分页
		QueryWrapper<CourseMaterialsEntity> queryWrapper = new QueryWrapper<>();
		CommonUtil.handleSortQuery(queryWrapper, filterCourseMaterialListDto.getSortArray(), "course_materials");
		queryWrapper.like(StrUtil.isNotBlank(filterCourseMaterialListDto.getName()),"course_materials.name",filterCourseMaterialListDto.getName())
				.eq(filterCourseMaterialListDto.getCourseId() !=null,"course_materials.course_id",filterCourseMaterialListDto.getCourseId())
				.eq(filterCourseMaterialListDto.getStatus() !=null,"course_materials.status",filterCourseMaterialListDto.getStatus())
				.eq(StrUtil.isNotBlank(filterCourseMaterialListDto.getCreaterAccount()),"course_materials.creater_account",filterCourseMaterialListDto.getCreaterAccount())
				.ge(StrUtil.isNotBlank(filterCourseMaterialListDto.getStartTime()),"course_materials.create_time",filterCourseMaterialListDto.getStartTime())
				.le(StrUtil.isNotBlank(filterCourseMaterialListDto.getEndTime()),"course_materials.create_time",filterCourseMaterialListDto.getEndTime());
		return courseMaterialMapper.filterCourseMaterialList(page,queryWrapper);
	}

	@Override
	public Integer createCourseMaterial(CourseMaterialsEntity courseMaterialsEntity) {
		return courseMaterialMapper.insert(courseMaterialsEntity);
	}

	@Override
	public Integer deleteCourseMaterial(Integer id) {
		return courseMaterialMapper.deleteById(id);
	}

	@Override
	public CourseMaterialsEntity getCourseMaterialById(Integer id) {
		return courseMaterialMapper.selectById(id);
	}

	@Override
	public Integer updateCourseMaterial(CourseMaterialsEntity courseMaterialsEntity) {
		return courseMaterialMapper.updateById(courseMaterialsEntity);
	}

	@Override
	public List<CourseMaterialsEntity> getCourseMaterialByCourse(Integer courseId) {
//		Map map = new HashMap<>();
//		map.put("course_id",courseId);
//		map.put("status",1);
		return courseMaterialMapper.getCourseMaterialByCourse(courseId);
	}
}