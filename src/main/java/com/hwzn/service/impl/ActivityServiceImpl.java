package com.hwzn.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.mapper.ActivityMapper;
import com.hwzn.pojo.dto.activity.GetActivityListByPageDto;
import com.hwzn.pojo.entity.ActivityEntity;
import com.hwzn.service.ActivityService;
import com.hwzn.util.CommonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:41
 * @Desc: 横幅
 */
@Service
public class ActivityServiceImpl implements ActivityService {
	
	@Resource
	private ActivityMapper activityMapper;

	@Override
	public void createActivity(ActivityEntity activityEntity) {
		activityMapper.insert(activityEntity);
	}

	@Override
	public ActivityEntity getActivityById(Integer id) {
		return activityMapper.selectById(id);
	}

	@Override
	public void updateActivity(ActivityEntity activityEntity) {
		activityMapper.updateById(activityEntity);
	}

	@Override
	public void updateActivityStatus(ActivityEntity activityEntity) {
		activityMapper.updateActivityStatus(activityEntity);
	}

	@Override
	public void deleteActivity(Integer id) {
		activityMapper.deleteById(id);
	}

	@Override
	public IPage<ActivityEntity> getActivityListByPage(GetActivityListByPageDto getActivityListByPageDto) {
		Page<ActivityEntity> page = new Page<>(getActivityListByPageDto.getPageNum(),getActivityListByPageDto.getPageSize());//分页
		ActivityEntity activityEntity = new ActivityEntity();
		BeanUtils.copyProperties(getActivityListByPageDto,activityEntity);
		QueryWrapper<ActivityEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.like(StrUtil.isNotBlank(getActivityListByPageDto.getTitle()),"title",getActivityListByPageDto.getTitle())
				.like(StrUtil.isNotBlank(getActivityListByPageDto.getAuthor()),"author",getActivityListByPageDto.getAuthor())
				.eq(getActivityListByPageDto.getStatus() != null,"status",getActivityListByPageDto.getStatus())
				.ge(StrUtil.isNotBlank(getActivityListByPageDto.getStartTime()),"submit_time",getActivityListByPageDto.getStartTime())
				.le(StrUtil.isNotBlank(getActivityListByPageDto.getEndTime()),"submit_time",getActivityListByPageDto.getEndTime());

		CommonUtil.handleSortQuery(queryWrapper, getActivityListByPageDto.getSortArray(), "activitys");

		return activityMapper.selectListPage(page,queryWrapper);
	}

	@Override
	public ActivityEntity getActivityInfoByID(Integer id) {
		return activityMapper.selectById(id);
	}

}
