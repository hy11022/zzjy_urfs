package com.hwzn.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.pojo.dto.button.GetButtonListByPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.button.UpdateButtonDto;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.hwzn.pojo.entity.ButtonEntity;
import com.hwzn.pojo.entity.RouterEntity;
import com.hwzn.service.ButtonService;
import com.hwzn.mapper.RouterMapper;
import com.hwzn.mapper.ButtonMapper;
import cn.hutool.core.util.StrUtil;
import javax.annotation.Resource;
import com.hwzn.pojo.Result;
import java.util.List;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:41
 * @Desc: 按钮
 */
@Service
public class ButtonServiceImpl implements ButtonService {
	
	@Resource
	private ButtonMapper buttonMapper;
	@Resource
	private RouterMapper routerMapper;

	@Override
	public Integer createButton(ButtonEntity buttonEntity) {
		return buttonMapper.insert(buttonEntity);
	}

	@Override
	public IPage<ButtonEntity> getButtonList(GetButtonListByPageDto getButtonListByPageDto) {
		Page<ButtonEntity> page = new Page<>(getButtonListByPageDto.getPageNum(),getButtonListByPageDto.getPageSize());//分页
		ButtonEntity buttonEntity = new ButtonEntity();
		BeanUtils.copyProperties(getButtonListByPageDto,buttonEntity);

		QueryWrapper<ButtonEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(getButtonListByPageDto.getRouterId() != null,"buttons.router_id",getButtonListByPageDto.getRouterId())
				.eq(StrUtil.isNotBlank(getButtonListByPageDto.getCode()),"buttons.code",getButtonListByPageDto.getCode())
				.like(StrUtil.isNotBlank(getButtonListByPageDto.getName()),"buttons.name",getButtonListByPageDto.getName());
		return buttonMapper.selectListPage(page,queryWrapper);
	}

	@Override
	public ButtonEntity getButtonById(Integer id) {
		return buttonMapper.selectById(id);
	}

	@Override
	public Result validateCreateParam(Integer id) {
		RouterEntity routerEntity = routerMapper.selectById(id);
		if(routerEntity == null){
			return Result.showInfo(2,"指定的路由不存在",null);
		}
		return Result.showInfo(0,"Success",null);
	}

	@Override
	public Result validateUpdateParam(UpdateButtonDto updateButtonDto) {
		RouterEntity routerEntity = routerMapper.selectById(updateButtonDto.getRouterId());
		if(routerEntity==null){
			return Result.showInfo(2,"指定的路由不存在",null);
		}
		ButtonEntity buttonEntity = buttonMapper.selectById(updateButtonDto.getId());
		if(buttonEntity==null){
			return Result.showInfo(3,"指定的按钮不存在",null);
		}
		return Result.showInfo(0,"Success",null);
	}

	@Override
	public Integer updateButton(ButtonEntity buttonEntity) {
		return buttonMapper.updateById(buttonEntity);
	}

	@Override
	public Integer deleteButton(Integer id) {
		return buttonMapper.deleteById(id);
	}

	@Override
	public List<RouterEntity> getRouterButtonTree() {
		QueryWrapper<RouterEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(RouterEntity::getLevel,1);
		queryWrapper.orderByAsc("seq");
		List<RouterEntity> resultList = routerMapper.selectList(queryWrapper);
		getRouterChildList(resultList);
		return resultList;
	}

	//递归查询所有
	public void getRouterChildList(List<RouterEntity> routerList) {
		for (RouterEntity routerEntity : routerList) {
			QueryWrapper<RouterEntity> queryWrapper = new QueryWrapper<>();
			queryWrapper.lambda().eq(RouterEntity::getParId, routerEntity.getId());
			queryWrapper.orderByAsc("seq");
			List<RouterEntity> childrenList = routerMapper.selectList(queryWrapper);
			routerEntity.setChildren(childrenList);
			if (CollUtil.isEmpty(childrenList)) {
				if (routerEntity.getType()==2) {
					QueryWrapper<ButtonEntity> buttonQueryWrapper = new QueryWrapper<>();
					buttonQueryWrapper.lambda().eq(ButtonEntity::getRouterId,routerEntity.getId() );
					List<ButtonEntity> buttonChildList = buttonMapper.selectList(buttonQueryWrapper);
					for (ButtonEntity buttonEntity:buttonChildList){
						buttonEntity.setType(3);
					}
					routerEntity.setChildren(buttonChildList);
				}
			} else {
				getRouterChildList(childrenList);

			}
		}
	}
}
