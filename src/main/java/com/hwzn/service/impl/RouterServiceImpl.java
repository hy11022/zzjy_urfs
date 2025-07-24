package com.hwzn.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hwzn.pojo.dto.router.CreateRouterDto;
import com.hwzn.pojo.dto.router.UpdateRouterDto;
import org.springframework.stereotype.Service;
import com.hwzn.pojo.entity.ButtonEntity;
import com.hwzn.pojo.entity.RouterEntity;
import com.hwzn.service.RouterService;
import com.hwzn.mapper.RouterMapper;
import com.hwzn.mapper.ButtonMapper;
import javax.annotation.Resource;
import com.hwzn.pojo.Result;
import java.util.HashMap;
import java.util.Objects;
import java.util.List;
import java.util.Map;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:41
 * @Desc: 组织
 */
@Service
public class RouterServiceImpl implements RouterService {
	
	@Resource
	private RouterMapper routerMapper;

	@Resource
	private ButtonMapper buttonMapper;

	@Override
	public void createRouter(RouterEntity routerEntity) {
        routerMapper.insert(routerEntity);
    }

	@Override
	public Result validateCreateParam(CreateRouterDto createRouterDto) {
		RouterEntity parRouterEntity = getRouterById(createRouterDto.getParId());
		if(createRouterDto.getParId()==0){
			if(createRouterDto.getLevel() !=1){
				return Result.showInfo(3,"层级有误，父级路由为最上层时层级只能为1",null);
			}
			if(createRouterDto.getType()==2){
				return Result.showInfo(4,"错误，页面路由不可作为最上层",null);
			}
		}else{
			if(parRouterEntity==null){
				return Result.showInfo(5,"父级路由不存在",null);
			}
			if(createRouterDto.getLevel() != parRouterEntity.getLevel()+1){
				return Result.showInfo(6,"层级有误，路由层级应比父级路由层级大1",null);
			}
			if(parRouterEntity.getType()==2){
				return Result.showInfo(7,"错误，页面路由不可作为父路由",null);
			}
		}
		return Result.showInfo(0,"Success", null);
	}

	@Override
	public RouterEntity getRouterById(Integer id) {
		return routerMapper.selectById(id);
	}

	@Override
	public List<RouterEntity> getRouterByParId(Integer id) {
		Map<String, Object> columnMap = new HashMap<>();
		columnMap.put("par_id",id);
		return routerMapper.selectByMap(columnMap);
	}

	@Override
	public List<RouterEntity> getRouterList() {
		QueryWrapper<RouterEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("level",1);
		queryWrapper.orderByAsc("seq");
		List<RouterEntity> routerList = routerMapper.selectList(queryWrapper);
		getRouterChildList(routerList);
		return routerList;
	}

	@Override
	public List<RouterEntity> selectRouterList(QueryWrapper<RouterEntity> routerQueryWrapper) {
		return routerMapper.selectList(routerQueryWrapper);
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
				routerEntity.setChildren(null);
			} else {
				getRouterChildList(childrenList);
			}
		}
	}

	@Override
	public void updateRouter(RouterEntity routerEntity) {
		routerMapper.updateById(routerEntity);
	}

	@Override
	public Result validateUpdateParam(UpdateRouterDto updateRouterDto) {
		if(Objects.equals(updateRouterDto.getId(), updateRouterDto.getParId())){
			return Result.showInfo(2,"ID不能与父级ID相同",null);
		}
		RouterEntity routerEntity1 = routerMapper.selectById(updateRouterDto.getId());
		RouterEntity routerEntity2 = routerMapper.selectById(updateRouterDto.getParId());
		if(routerEntity1==null || (routerEntity2==null && updateRouterDto.getParId()!=0)){
			return Result.showInfo(3,"路由或父级路由不存在",null);
		}
		if(updateRouterDto.getParId()==0){
			if(updateRouterDto.getLevel() !=1){
				return Result.showInfo(4,"层级有误，父级路由为最上层时层级只能为1",null);
			}
			if(updateRouterDto.getType()==2){
				return Result.showInfo(5,"错误，页面路由不可作为最上层",null);
			}
		}else {
			if(updateRouterDto.getLevel() != routerEntity2.getLevel()+1){
				return Result.showInfo(6,"层级有误，路由层级应比父级路由层级大1",null);
			}
			if(routerEntity2.getType()==2){
				return Result.showInfo(7,"错误，页面路由不可作为父路由",null);
			}
		}
		return Result.showInfo(0,"Success", null);
	}

	@Override
	public void deleteRouter(Integer id) {
		routerMapper.deleteById(id);
	}

	public List<ButtonEntity> getButtonByRouterId(Integer id) {
		Map<String, Object> columnMap = new HashMap<>();
		columnMap.put("router_id",id);
		return buttonMapper.selectByMap(columnMap);
	}

	@Override
	public Result validateDeleteParam(RouterEntity routerEntity) {
		if(routerEntity==null){
			return Result.showInfo(2,"路由不存在",null);
		}
		//判断菜单是否有下辖路由，有则不可删除
		if(routerEntity.getType()==1){
			List<RouterEntity> routerList = getRouterByParId(routerEntity.getId());
			if(!CollUtil.isEmpty(routerList)){
				return Result.showInfo(3,"请先删除下辖路由",null);
			}
		}else{//判断页面是否有按钮，有则不可删除
			List<ButtonEntity> buttonList = getButtonByRouterId(routerEntity.getId());
			if(!CollUtil.isEmpty(buttonList)){
				return Result.showInfo(4,"请先删除页面按钮",null);
			}
		}
		return Result.showInfo(0,"Success", null);
	}
}