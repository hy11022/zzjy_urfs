package com.hwzn.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.servlet.http.HttpServletRequest;
import com.hwzn.pojo.dto.dept.CreateDeptDto;
import com.hwzn.pojo.dto.dept.UpdateDeptDto;
import org.springframework.beans.BeanUtils;
import com.hwzn.pojo.entity.DeptEntity;
import com.hwzn.pojo.dto.common.IdDto;
import cn.hutool.json.JSONObject;
import javax.annotation.Resource;
import com.hwzn.util.CommonUtil;
import cn.hutool.json.JSONUtil;
import com.hwzn.util.JWTUtil;
import com.hwzn.pojo.Result;
import com.hwzn.service.*;
import java.util.List;

/**
 * @Author: hy
 * @Date: 2023/9/6 13:12
 * @Desc: 组织
 */

@RestController
@RequestMapping("/dept")
public class DeptController {

	@Resource
	DeptService deptService;
	
	@Resource
	UserLogService userLogService;

	@Resource
	DbTbLogService dbTbLogService;

	//创建部门
	@PostMapping("/createDept")
	public Result createDept(@Validated @RequestBody CreateDeptDto createDeptDto, HttpServletRequest request){
		//验证入参逻辑
		Result validateCreateParam =deptService.validateCreateParam(createDeptDto);
		if(validateCreateParam.getCode()!=0){
			return Result.showInfo(validateCreateParam.getCode(),validateCreateParam.getMsg(),null);
		}
		DeptEntity deptEntity = new DeptEntity();
		BeanUtils.copyProperties(createDeptDto,deptEntity);
		//创建新部门
		deptService.createDept(deptEntity);
		if(deptEntity.getId() ==null){
			return Result.showInfo(2,"创建失败",null);
		}
		JSONObject data =new JSONObject();
		data.set("id",deptEntity.getId());

		//记录用户日志
		userLogService.createUserLog(CommonUtil.getUserLog("创建部门,内容为："+JSONUtil.toJsonStr(deptEntity),JWTUtil.getAccount(request.getHeader("token"))));
		//记录数据库日志
		dbTbLogService.createDbTbLog(CommonUtil.getDbTbLog("depts",deptEntity.getId(),"创建",JWTUtil.getAccount(request.getHeader("token"))));
		return Result.showInfo(0,"Success",data);
	}

	//获取部门列表
	@PostMapping("/getDeptList")
	public Result getDeptList(){
		JSONObject data=new JSONObject();
		//获取组织列表
		List<DeptEntity> resultList = deptService.getDeptList();
		data.set("records",resultList);
		return Result.showInfo(0,"Success", data);
	}

	//更新部门
	@PostMapping("/updateDept")
	public Result updateDept(@Validated @RequestBody UpdateDeptDto updateDeptDto, HttpServletRequest request){
		//验证入参逻辑
		Result validateUpdateParam =deptService.validateUpdateParam(updateDeptDto);
		if(validateUpdateParam.getCode()!=0){
			return Result.showInfo(validateUpdateParam.getCode(),validateUpdateParam.getMsg(),null);
		}
		DeptEntity deptEntity = new DeptEntity();
		BeanUtils.copyProperties(updateDeptDto,deptEntity);
		//更新部门
		deptService.updateDept(deptEntity);
		//记录用户日志
		userLogService.createUserLog(CommonUtil.getUserLog("更新部门,内容为："+JSONUtil.toJsonStr(deptEntity),JWTUtil.getAccount(request.getHeader("token"))));
		//记录数据库日志
		dbTbLogService.createDbTbLog(CommonUtil.getDbTbLog("depts",deptEntity.getId(),"更新",JWTUtil.getAccount(request.getHeader("token"))));
		return Result.showInfo(0,"Success",null);
	}

	//删除部门
	@PostMapping("/deleteDept")
	public Result deleteDept(@Validated @RequestBody IdDto idDto, HttpServletRequest request){
		//先判断ID指定的部门是否存在
		DeptEntity deptEntity1 = deptService.getDeptById(idDto.getId());
		//验证入参逻辑
		Result validateDeleteParam =deptService.validateDeleteParam(deptEntity1);
		if(validateDeleteParam.getCode()!=0){
			return Result.showInfo(validateDeleteParam.getCode(),validateDeleteParam.getMsg(),null);
		}
		deptService.deleteDept(idDto.getId());
		//记录用户日志
		userLogService.createUserLog(CommonUtil.getUserLog("删除部门,ID为："+ deptEntity1.getId(),JWTUtil.getAccount(request.getHeader("token"))));
		return Result.showInfo(0,"Success", null);
	}
}