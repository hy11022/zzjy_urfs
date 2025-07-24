package com.hwzn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.pojo.dto.config.GetConfigListByPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.hwzn.pojo.entity.ConfigsEntity;
import com.hwzn.service.ConfigService;
import com.hwzn.mapper.ConfigMapper;
import cn.hutool.core.util.StrUtil;
import javax.annotation.Resource;
import com.hwzn.pojo.Result;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:41
 * @Desc: 组织
 */
@Service
public class ConfigServiceImpl implements ConfigService {
	
	@Resource
	private ConfigMapper configMapper;

	@Override
	public Integer createConfig(ConfigsEntity configsEntity) {
		return configMapper.insert(configsEntity);
	}

	@Override
	public Integer updateConfig(ConfigsEntity configsEntity) {
		return configMapper.updateById(configsEntity);
	}

	@Override
	public Result validateUpdateParam(Integer id) {
		ConfigsEntity configsEntity = configMapper.selectById(id);
		if(configsEntity==null){
			return Result.showInfo(2,"指定的配置不存在",null);
		}
		return Result.showInfo(0,"Success",null);
	}

	@Override
	public void deleteConfig(Integer id) {
		configMapper.deleteById(id);
	}

	@Override
	public IPage<ConfigsEntity> getButtonList(GetConfigListByPageDto getConfigListByPageDto) {
		Page<ConfigsEntity> page = new Page<>(getConfigListByPageDto.getPageNum(),getConfigListByPageDto.getPageSize());//分页
		ConfigsEntity configsEntity = new ConfigsEntity();
		BeanUtils.copyProperties(getConfigListByPageDto,configsEntity);

		QueryWrapper<ConfigsEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(getConfigListByPageDto.getType() != null,"type",getConfigListByPageDto.getType())
				.eq(StrUtil.isNotBlank(getConfigListByPageDto.getCode()),"code",getConfigListByPageDto.getCode())
				.like(StrUtil.isNotBlank(getConfigListByPageDto.getName()),"name",getConfigListByPageDto.getName());
		return configMapper.selectPage(page,queryWrapper);
	}

	@Override
	public String getConfigContentByCode(String code) {
		QueryWrapper<ConfigsEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("code",code);
		ConfigsEntity configsEntity =configMapper.selectOne(queryWrapper);
		if(configsEntity!=null){
			return configsEntity.getContent();
		}
		return null;
	}

	@Override
	public ConfigsEntity getConfigInfoByCode(String code) {
		QueryWrapper<ConfigsEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("code",code);
        return configMapper.selectOne(queryWrapper);
	}

	@Override
	public ConfigsEntity getConfigsInfoByID(Integer id) {
		return configMapper.selectById(id);

	}
}