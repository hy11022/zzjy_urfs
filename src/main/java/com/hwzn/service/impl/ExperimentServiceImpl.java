package com.hwzn.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.mapper.ExperimentMapper;
import com.hwzn.pojo.dto.experiment.FilterExperimentListDto;
import com.hwzn.pojo.dto.experiment.GetMyExperimentListDto;
import com.hwzn.pojo.entity.ExperimentEntity;
import com.hwzn.pojo.vo.ExperimentInfoVo;
import com.hwzn.service.ExperimentService;
import com.hwzn.util.CommonUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @Author：Rongjun Du
 * @Date：2023/11/29 11:02
 * @Desc：实验服务实现类
 */
@Service
public class ExperimentServiceImpl implements ExperimentService {
	
	@Resource
	ExperimentMapper experimentMapper;
	
	@Override
	public IPage<ExperimentEntity> filterList(FilterExperimentListDto filterExperimentListDto) {
		Page<ExperimentEntity> page =new Page<>(filterExperimentListDto.getPageNum(),filterExperimentListDto.getPageSize());
		QueryWrapper<ExperimentEntity> queryWrapper = new QueryWrapper<>();
		CommonUtil.handleSortQuery(queryWrapper, filterExperimentListDto.getSortArray(), "experiments");
		queryWrapper.like(StringUtils.isNotBlank(filterExperimentListDto.getName()), "experiments.name", filterExperimentListDto.getName())
				.eq(StringUtils.isNotBlank(filterExperimentListDto.getCreaterAccount()), "experiments.creater_account", filterExperimentListDto.getCreaterAccount())
				.ge(StrUtil.isNotBlank(filterExperimentListDto.getStartTime()),"experiments.create_time",filterExperimentListDto.getStartTime())
				.le(StrUtil.isNotBlank(filterExperimentListDto.getEndTime()),"experiments.create_time",filterExperimentListDto.getEndTime());
		return experimentMapper.filterList(page,queryWrapper);
	}
	
	@Override
	public IPage<ExperimentEntity> filterMyExperimentList(GetMyExperimentListDto getMyExperimentListDto) {
		Page<ExperimentEntity> page = new Page<>(getMyExperimentListDto.getPageNum(),getMyExperimentListDto.getPageSize());
		QueryWrapper<ExperimentEntity> queryWrapper = new QueryWrapper<>();
		CommonUtil.handleSortQuery(queryWrapper, getMyExperimentListDto.getSortArray(), "experiments");
		queryWrapper.like(StringUtils.isNotBlank(getMyExperimentListDto.getName()), "experiments.name", getMyExperimentListDto.getName())
				.eq(StringUtils.isNotBlank(getMyExperimentListDto.getAccount()), "experiments.creater_account", getMyExperimentListDto.getAccount())
				.ge(StrUtil.isNotBlank(getMyExperimentListDto.getStartTime()),"experiments.create_time",getMyExperimentListDto.getStartTime())
				.le(StrUtil.isNotBlank(getMyExperimentListDto.getEndTime()),"experiments.create_time",getMyExperimentListDto.getEndTime());
		return experimentMapper.selectPage(page,queryWrapper);
	}
	
	@Override
	public ExperimentEntity getEntityById(Integer id) {
		return experimentMapper.selectById(id);
	}
	
	@Override
	public ExperimentInfoVo getInfoById(Integer id) {
		return experimentMapper.getInfoById(id);
	}
	
	@Override
	public Integer create(ExperimentEntity experimentEntity) {
		return experimentMapper.insert(experimentEntity);
	}
	
	@Override
	public Integer updateInfo(ExperimentEntity experimentEntity) {
		return experimentMapper.updateById(experimentEntity);
	}
	
	@Override
	public Integer update(ExperimentEntity experimentEntity) {
		return experimentMapper.updateById(experimentEntity);
	}
	
	@Override
	public Integer deleteById(Integer id) {
		return experimentMapper.deleteById(id);
	}
	
	@Override
	public Long countByProjectId(Integer projectId) {
		QueryWrapper<ExperimentEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("experiments.project_id", projectId);
		return experimentMapper.selectCount(queryWrapper);
	}
	
}
