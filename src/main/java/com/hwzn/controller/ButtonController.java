package com.hwzn.controller;

import java.util.List;
import com.hwzn.pojo.Result;
import com.hwzn.util.JWTUtil;
import cn.hutool.json.JSONUtil;
import com.hwzn.util.CommonUtil;
import cn.hutool.json.JSONObject;
import javax.annotation.Resource;
import com.hwzn.pojo.dto.common.IdDto;
import com.hwzn.service.ButtonService;
import com.hwzn.service.DbTbLogService;
import com.hwzn.service.UserLogService;
import com.hwzn.pojo.entity.ButtonEntity;
import com.hwzn.pojo.entity.RouterEntity;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import com.hwzn.pojo.dto.button.CreateButtonDto;
import com.hwzn.pojo.dto.button.UpdateButtonDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.button.GetButtonListByPageDto;
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
@RequestMapping("/button")
public class ButtonController {

	@Resource
	ButtonService buttonService;
	
	@Resource
	UserLogService userLogService;

	@Resource
	DbTbLogService dbTbLogService;

	//创建按钮
	@PostMapping("/createButton")
	public Result createButton(@Validated @RequestBody CreateButtonDto createButtonDto, HttpServletRequest request){
		//验证入参逻辑
		Result validateCreateParam =buttonService.validateCreateParam(createButtonDto.getRouterId());
		if(validateCreateParam.getCode()!=0){
			return Result.showInfo(validateCreateParam.getCode(),validateCreateParam.getMsg(),null);
		}
		ButtonEntity buttonEntity = new ButtonEntity();
		BeanUtils.copyProperties(createButtonDto,buttonEntity);
		//创建新按钮
		buttonService.createButton(buttonEntity);

		JSONObject data = new JSONObject();
		data.set("id",buttonEntity.getId());
		if(buttonEntity.getId() == null){
			return Result.showInfo(3,"创建失败",null);
		}
		//记录用户日志
		userLogService.createUserLog(CommonUtil.getUserLog("创建按钮,内容为："+JSONUtil.toJsonStr(buttonEntity),JWTUtil.getAccount(request.getHeader("token"))));
		//记录数据库日志
		dbTbLogService.createDbTbLog(CommonUtil.getDbTbLog("buttons",buttonEntity.getId(),"创建",JWTUtil.getAccount(request.getHeader("token"))));
		return Result.showInfo(0,"Success",data);
	}

	//获取按钮列表（分页）
	@PostMapping("/getButtonListByPage")
	public Result getButtonListByPage(@Validated @RequestBody GetButtonListByPageDto getButtonListByPageDto){
		//获取组织列表
		IPage<ButtonEntity> resultList = buttonService.getButtonList(getButtonListByPageDto);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}

	//更新按钮
	@PostMapping("/updateButton")
	public Result updateButton(@Validated @RequestBody UpdateButtonDto updateButtonDto, HttpServletRequest request){
		//验证入参逻辑
		Result validateUpdateParam =buttonService.validateUpdateParam(updateButtonDto);
		if(validateUpdateParam.getCode()!=0){
			return Result.showInfo(validateUpdateParam.getCode(),validateUpdateParam.getMsg(),null);
		}
		//赋值
		ButtonEntity buttonEntity = new ButtonEntity();
		BeanUtils.copyProperties(updateButtonDto,buttonEntity);
		//更新按钮
		buttonService.updateButton(buttonEntity);
		//记录用户日志
		userLogService.createUserLog(CommonUtil.getUserLog("更新按钮,内容为："+JSONUtil.toJsonStr(buttonEntity),JWTUtil.getAccount(request.getHeader("token"))));
		//记录数据库日志
		dbTbLogService.createDbTbLog(CommonUtil.getDbTbLog("buttons",buttonEntity.getId(),"更新",JWTUtil.getAccount(request.getHeader("token"))));
		return Result.showInfo(0,"Success",null);
	}

	//删除按钮
	@PostMapping("/deleteButton")
	public Result deleteButton(@Validated @RequestBody IdDto idDto, HttpServletRequest request){
		//先判断ID指定的按钮是否存在
		ButtonEntity buttonEntity = buttonService.getButtonById(idDto.getId());
		if(buttonEntity == null){
			return Result.showInfo(2,"指定按钮不存在",null);
		}
		buttonService.deleteButton(idDto.getId());
		//记录用户日志
		userLogService.createUserLog(CommonUtil.getUserLog("删除按钮,ID为："+ idDto.getId(),JWTUtil.getAccount(request.getHeader("token"))));
		return Result.showInfo(0,"Success", null);
	}

	//获取路由按钮树
	@PostMapping("/getRouterButtonTree")
	public Result getAppRouterButtonTree() {
		//获取路由按钮树
		List<RouterEntity> resultList = buttonService.getRouterButtonTree();
		JSONObject data=new JSONObject();
		data.set("records",resultList);
		return Result.showInfo(0,"Success", data);
	}
}
