package com.hwzn.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.mapper.ClassMapper;
import com.hwzn.pojo.dto.classes.FilterClassListDto;
import com.hwzn.pojo.entity.ClassEntity;
import com.hwzn.service.ClassService;
import com.hwzn.util.CommonUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @Author：Rongjun Du
 * @Date：2024/2/20 16:41
 * @Desc：班级服务实现类
 */
@Service
public class ClassServiceImpl implements ClassService {
	
	@Resource
	ClassMapper classMapper;
	
	@Override
	public IPage<ClassEntity> filterList(FilterClassListDto filterClassListDto) {
		Page<ClassEntity> page = new Page<>(filterClassListDto.getPageNum(), filterClassListDto.getPageSize());
		QueryWrapper<ClassEntity> queryWrapper = new QueryWrapper<>();
		CommonUtil.handleSortQuery(queryWrapper, filterClassListDto.getSortArray(), "classes");
		queryWrapper.like(StrUtil.isNotBlank(filterClassListDto.getName()), "classes.name", filterClassListDto.getName());
		queryWrapper.like(StrUtil.isNotBlank(filterClassListDto.getCode()), "classes.code", filterClassListDto.getCode());
		return classMapper.selectPage(page, queryWrapper);
	}

	@Override
	public Integer create(ClassEntity classEntity) {
		return classMapper.insert(classEntity);
	}
	
	@Override
	public Integer update(ClassEntity classEntity) {
		return classMapper.updateById(classEntity);
	}
	
	@Override
	public Integer delete(Integer id) {
		return classMapper.deleteById(id);
	}
}
