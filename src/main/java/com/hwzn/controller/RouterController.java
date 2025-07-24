package com.hwzn.controller;

import java.util.List;
import com.hwzn.pojo.Result;
import com.hwzn.util.JWTUtil;
import cn.hutool.json.JSONUtil;
import com.hwzn.util.CommonUtil;
import cn.hutool.json.JSONObject;
import javax.annotation.Resource;
import com.hwzn.pojo.dto.common.IdDto;
import com.hwzn.service.RouterService;
import com.hwzn.service.DbTbLogService;
import com.hwzn.service.UserLogService;
import com.hwzn.pojo.entity.RouterEntity;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import com.hwzn.pojo.dto.router.UpdateRouterDto;
import com.hwzn.pojo.dto.router.CreateRouterDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: hy
 * @Date: 2023/9/6 13:12
 * @Desc: 组织
 */
@RestController
@RequestMapping("/router")
public class RouterController {

	@Resource
	RouterService routerService;
	
	@Resource
	UserLogService userLogService;

	@Resource
	DbTbLogService dbTbLogService;

	//创建路由
	@PostMapping("/createRouter")
	public Result createRouter(@Validated @RequestBody CreateRouterDto createRouterDto, HttpServletRequest request){
		//验证入参逻辑
		Result validateCreateParam =routerService.validateCreateParam(createRouterDto);
		if(validateCreateParam.getCode()!=0){
			return Result.showInfo(validateCreateParam.getCode(),validateCreateParam.getMsg(),null);
		}

		RouterEntity routerEntity = new RouterEntity();
		BeanUtils.copyProperties(createRouterDto,routerEntity);
		//创建新路由
		routerService.createRouter(routerEntity);

		JSONObject data =new JSONObject();
		data.set("id",routerEntity.getId());
		if(routerEntity.getId() ==null){
			return Result.showInfo(2,"创建失败",null);
		}
		//记录用户日志
		userLogService.createUserLog(CommonUtil.getUserLog("创建路由,内容为："+JSONUtil.toJsonStr(routerEntity),JWTUtil.getAccount(request.getHeader("token"))));
		//记录数据库日志
		dbTbLogService.createDbTbLog(CommonUtil.getDbTbLog("routers",routerEntity.getId(),"创建",JWTUtil.getAccount(request.getHeader("token"))));
		return Result.showInfo(0,"Success",data);
	}

	//获取路由列表
	@PostMapping("/getRouterList")
	public Result getRouterList(){
		JSONObject data=new JSONObject();
		//获取路由列表
		List<RouterEntity> resultList = routerService.getRouterList();
		data.set("records",resultList);
		return Result.showInfo(0,"Success", data);
	}

	//更新路由
	@PostMapping("/updateRouter")
	public Result updateRouter(@Validated @RequestBody UpdateRouterDto updateRouterDto, HttpServletRequest request){
		//验证入参逻辑
		Result validateUpdateParam =routerService.validateUpdateParam(updateRouterDto);
		if(validateUpdateParam.getCode()!=0){
			return Result.showInfo(validateUpdateParam.getCode(),validateUpdateParam.getMsg(),null);
		}

		RouterEntity routerEntity = new RouterEntity();
		BeanUtils.copyProperties(updateRouterDto,routerEntity);
		//更新路由
		routerService.updateRouter(routerEntity);
		//记录用户日志
		userLogService.createUserLog(CommonUtil.getUserLog("更新路由,内容为："+JSONUtil.toJsonStr(routerEntity),JWTUtil.getAccount(request.getHeader("token"))));
		//记录数据库日志
		dbTbLogService.createDbTbLog(CommonUtil.getDbTbLog("routers",routerEntity.getId(),"更新",JWTUtil.getAccount(request.getHeader("token"))));
		return Result.showInfo(0,"Success",null);
	}

	//删除路由
	@PostMapping("/deleteRouter")
	public Result deleteRouter(@Validated @RequestBody IdDto idDto, HttpServletRequest request){
		//先判断ID指定的路由是否存在
		RouterEntity routerEntity = routerService.getRouterById(idDto.getId());
		//验证入参逻辑
		Result validateDeleteParam =routerService.validateDeleteParam(routerEntity);
		if(validateDeleteParam.getCode()!=0){
			return Result.showInfo(validateDeleteParam.getCode(),validateDeleteParam.getMsg(),null);
		}
		routerService.deleteRouter(idDto.getId());
		//记录用户日志
		userLogService.createUserLog(CommonUtil.getUserLog("删除路由,ID为："+ routerEntity.getId(),JWTUtil.getAccount(request.getHeader("token"))));

		return Result.showInfo(0,"Success", null);
	}
}
