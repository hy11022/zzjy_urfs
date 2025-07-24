package com.hwzn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.role.GetRoleListByPageDto;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import com.hwzn.pojo.entity.UserEntity;
import com.hwzn.pojo.entity.RoleEntity;
import com.hwzn.service.RoleService;
import cn.hutool.core.util.StrUtil;
import com.hwzn.mapper.RoleMapper;
import com.hwzn.mapper.UserMapper;
import javax.annotation.Resource;
import com.hwzn.util.JWTUtil;

import java.util.List;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:41
 * @Desc: 组织
 */
@Service
public class RoleServiceImpl implements RoleService {
	
	@Resource
	private RoleMapper roleMapper;

	@Resource
	private UserMapper userMapper;

	@Override
	public void createRole(RoleEntity roleEntity) {
		roleMapper.insert(roleEntity);
	}

	@Override
	public IPage<RoleEntity> getRoleListByPage(GetRoleListByPageDto getRoleListByPageDto) {
		Page<RoleEntity> page = new Page<>(getRoleListByPageDto.getPageNum(),getRoleListByPageDto.getPageSize());//分页
		RoleEntity roleEntity = new RoleEntity();
		BeanUtils.copyProperties(getRoleListByPageDto,roleEntity);

		QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.like(StrUtil.isNotBlank(getRoleListByPageDto.getName()),"name",getRoleListByPageDto.getName());
		return roleMapper.selectPage(page,queryWrapper);
	}

	@Override
	public List<RoleEntity> getRoleList() {
		QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper<>();
		return roleMapper.selectList(queryWrapper);
	}

	@Override
	public void updateRole(RoleEntity roleEntity) {
		roleMapper.updateById(roleEntity);
	}

	@Override
	public RoleEntity getRoleById(Integer id) {
		return roleMapper.selectById(id);
	}

	@Override
	public void deleteRole(Integer id) {
		roleMapper.deleteById(id);
	}

	@Override
	public List<Object> getRoleByButtonAuthority(String buttonAuthority) {
		QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.like("button_authority",buttonAuthority);
		return roleMapper.selectObjs(queryWrapper);
	}

	@Override
	public List<RoleEntity> getAuthorityByAccount(List roleIdList) {
		return roleMapper.selectBatchIds(roleIdList);
	}

	@Override
	public Boolean hasAuthosity(HttpServletRequest request) {
		String account = JWTUtil.getAccount(request.getHeader("token"));
		//先判断account指定的用户是否有修改密码按钮权限user-center:user-management:restore-password
		QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("account",account);
		UserEntity userEntity = userMapper.selectOne(queryWrapper);

		String buttonAuthority = "user-center:user-management:restore-password";
		List<Object> roleList = getRoleByButtonAuthority(buttonAuthority);
		boolean hasAuthosity = false;
		if(userEntity != null){
			for (Object role:roleList){
			}
		}
		return hasAuthosity;
	}
}