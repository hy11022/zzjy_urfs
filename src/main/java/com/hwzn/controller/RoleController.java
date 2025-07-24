package com.hwzn.controller;

import java.util.List;
import com.hwzn.pojo.Result;
import com.hwzn.util.JWTUtil;
import cn.hutool.json.JSONUtil;
import com.hwzn.util.CommonUtil;
import cn.hutool.json.JSONObject;
import javax.annotation.Resource;
import com.hwzn.service.RoleService;
import com.hwzn.pojo.dto.common.IdDto;
import com.hwzn.service.DbTbLogService;
import com.hwzn.service.UserLogService;
import com.hwzn.pojo.entity.RoleEntity;
import org.springframework.beans.BeanUtils;
import com.hwzn.pojo.dto.role.UpdateRoleDto;
import com.hwzn.pojo.dto.role.CreateRoleDto;
import javax.servlet.http.HttpServletRequest;
import com.hwzn.pojo.dto.role.GetRoleListByPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: hy
 * @Date: 2023/9/6 13:12
 * @Desc: 组织
 */
@RestController
@RequestMapping("/role")
public class RoleController {

	@Resource
	RoleService roleService;

	@Resource
	UserLogService userLogService;

	@Resource
	DbTbLogService dbTbLogService;

	//创建角色
	@PostMapping("/createRole")
	public Result createRole(@Validated @RequestBody CreateRoleDto createRoleDto, HttpServletRequest request){
		RoleEntity roleEntity = new RoleEntity();
		BeanUtils.copyProperties(createRoleDto,roleEntity);
		//创建新角色
		roleService.createRole(roleEntity);
		JSONObject data =new JSONObject();
		data.set("id",roleEntity.getId());
		if(roleEntity.getId() ==null){
			return Result.showInfo(3,"创建失败",null);
		}
		//记录用户日志
		userLogService.createUserLog(CommonUtil.getUserLog("创建角色,内容为："+JSONUtil.toJsonStr(roleEntity),JWTUtil.getAccount(request.getHeader("token"))));
		//记录数据库日志
		dbTbLogService.createDbTbLog(CommonUtil.getDbTbLog("roles",roleEntity.getId(),"创建",JWTUtil.getAccount(request.getHeader("token"))));
		return Result.showInfo(0,"Success",data);
	}

	//获取角色列表（分页）
	@PostMapping("/getRoleListByPage")
	public Result getRoleListByPage(@Validated @RequestBody GetRoleListByPageDto getRoleListByPageDto){
		//获取角色列表
		IPage<RoleEntity> resultList = roleService.getRoleListByPage(getRoleListByPageDto);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}

	//获取角色列表
	@PostMapping("/getRoleList")
	public Result getRoleList(){
		//获取角色列表
		List<RoleEntity> resultList = roleService.getRoleList();
		JSONObject data=new JSONObject();
		data.set("records",resultList);
		return Result.showInfo(0,"Success", data);
	}

	//更新角色
	@PostMapping("/updateRole")
	public Result updateRole(@Validated @RequestBody UpdateRoleDto updateRoleDto, HttpServletRequest request){
		//赋值
		RoleEntity roleEntity = new RoleEntity();
		BeanUtils.copyProperties(updateRoleDto,roleEntity);
		//更新角色
		roleService.updateRole(roleEntity);
		//记录用户日志
		userLogService.createUserLog(CommonUtil.getUserLog("更新角色,内容为："+JSONUtil.toJsonStr(roleEntity),JWTUtil.getAccount(request.getHeader("token"))));
		//记录数据库日志
		dbTbLogService.createDbTbLog(CommonUtil.getDbTbLog("roles",roleEntity.getId(),"更新",JWTUtil.getAccount(request.getHeader("token"))));
		return Result.showInfo(0,"Success",null);
	}

	//删除角色
	@PostMapping("/deleteRole")
	public Result deleteRole(@Validated @RequestBody IdDto idDto, HttpServletRequest request){
		//先判断ID指定的按钮是否存在
		RoleEntity roleEntity = roleService.getRoleById(idDto.getId());
		if(roleEntity == null){
			return Result.showInfo(2,"指定角色不存在",null);
		}
		roleService.deleteRole(idDto.getId());
		//记录用户日志
		userLogService.createUserLog(CommonUtil.getUserLog("删除角色,ID为："+ roleEntity.getId(),JWTUtil.getAccount(request.getHeader("token"))));
		return Result.showInfo(0,"Success", null);
	}
}