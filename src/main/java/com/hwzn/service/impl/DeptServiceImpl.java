package com.hwzn.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.hwzn.pojo.dto.dept.UpdateDeptDto;
import com.hwzn.pojo.dto.dept.CreateDeptDto;
import com.hwzn.pojo.entity.UserEntity;
import com.hwzn.pojo.entity.DeptEntity;
import com.hwzn.service.DeptService;
import com.hwzn.mapper.UserMapper;
import com.hwzn.mapper.DeptMapper;
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
public class DeptServiceImpl implements DeptService {
	
	@Resource
	private DeptMapper deptMapper;

	@Resource
	private UserMapper userMapper;

	@Override
	public Integer createDept(DeptEntity deptEntity) {
		return deptMapper.insert(deptEntity);
	}

	@Override
	public List<DeptEntity> getDeptList() {
		QueryWrapper<DeptEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("level",1);
		queryWrapper.orderByAsc("seq");
		List<DeptEntity> deptList = deptMapper.selectList(queryWrapper);
		getDeptChildList(deptList);
		return deptList;
	}

	//递归查询所有
	public void getDeptChildList(List<DeptEntity> deptList) {
		for (DeptEntity deptEntity : deptList) {
			QueryWrapper<DeptEntity> queryWrapper = new QueryWrapper<>();
			queryWrapper.lambda().eq(DeptEntity::getParId, deptEntity.getId());
			queryWrapper.orderByAsc("seq");
			List<DeptEntity> childrenList = deptMapper.selectList(queryWrapper);
			deptEntity.setChildren(childrenList);
			if (CollUtil.isEmpty(childrenList)) {
				deptEntity.setChildren(null);
			} else {
				getDeptChildList(childrenList);
			}
		}
	}

	@Override
	public Integer updateDept(DeptEntity deptEntity) {
		return deptMapper.updateById(deptEntity);
	}

	@Override
	public DeptEntity getDeptById(Integer id) {
		return deptMapper.selectById(id);
	}

	@Override
	public List<DeptEntity> getDeptListByParId(Integer id) {
		Map<String, Object> columnMap = new HashMap<>();
		columnMap.put("par_id",id);
		return deptMapper.selectByMap(columnMap);
	}

	@Override
	public Integer deleteDept(Integer id) {
		return deptMapper.deleteById(id);
	}

	@Override
	public Result validateCreateParam(CreateDeptDto createDeptDto) {
		DeptEntity parDeptEntity = deptMapper.selectById(createDeptDto.getParId());
		if(createDeptDto.getParId()==0){
			if(createDeptDto.getLevel()!=1){
				return Result.showInfo(3,"层级有误，父级部门为最上层时层级只能为1",null);
			}
		}else {
			if(parDeptEntity==null){
				return Result.showInfo(4,"父级部门不存在",null);
			}
			if (createDeptDto.getLevel()!=parDeptEntity.getLevel()+1){
				return Result.showInfo(5,"层级有误，部门层级应比父级部门层级大1",null);
			}
		}
		return Result.showInfo(0,"Success",null);
	}

	@Override
	public Result validateUpdateParam(UpdateDeptDto updateDeptDto) {
		if(Objects.equals(updateDeptDto.getId(), updateDeptDto.getParId())){
			return Result.showInfo(2,"ID不能与父级ID相同",null);
		}
		DeptEntity deptEntity1 = getDeptById(updateDeptDto.getId());
		DeptEntity deptEntity2 = getDeptById(updateDeptDto.getParId());
		if(deptEntity1==null || (deptEntity2==null && updateDeptDto.getParId()!=0)){
			return Result.showInfo(3,"部门或父级部门不存在",null);
		}
		if(updateDeptDto.getParId()==0){
			if(updateDeptDto.getLevel()!=1){
				return Result.showInfo(4,"层级有误，父级部门为最上层时层级只能为1",null);
			}
		}else {
			if (updateDeptDto.getLevel()!=deptEntity2.getLevel()+1){
				return Result.showInfo(5,"层级有误，部门层级应比父级部门层级大1",null);
			}
		}
		return Result.showInfo(0,"Success",null);
	}

	public List<UserEntity> getUserByDeptId(Integer id) {
		QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("dept_id",id);
		return userMapper.selectList(queryWrapper);
	}

	@Override
	public Result validateDeleteParam(DeptEntity deptEntity1) {
		if(deptEntity1==null){
			return Result.showInfo(2,"部门不存在",null);
		}
		//判断是否有下辖部门，有则不可删除
		List<DeptEntity> deptList = getDeptListByParId(deptEntity1.getId());
		if(!CollUtil.isEmpty(deptList)){
			return Result.showInfo(3,"请先删除下辖部门",null);
		}
		//判断是否有下辖用户，有则不可删除
		List<UserEntity> userList = getUserByDeptId(deptEntity1.getId());
		if(!CollUtil.isEmpty(userList)){
			return Result.showInfo(4,"请先删除下辖用户",null);
		}
		return Result.showInfo(0,"Success",null);
	}
}