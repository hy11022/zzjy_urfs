package com.hwzn.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.hwzn.pojo.dto.config.GetConfigListByPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.config.UpdateConfigDto;
import com.hwzn.pojo.dto.config.CreateConfigDto;
import javax.servlet.http.HttpServletRequest;
import com.hwzn.service.UploadRecordService;
import org.springframework.beans.BeanUtils;
import com.hwzn.pojo.entity.ConfigsEntity;
import com.hwzn.pojo.dto.common.CodeDto;
import com.hwzn.service.DbTbLogService;
import com.hwzn.service.UserLogService;
import com.hwzn.service.ConfigService;
import com.hwzn.pojo.dto.common.IdDto;
import javax.annotation.Resource;
import cn.hutool.json.JSONObject;
import com.hwzn.util.CommonUtil;
import cn.hutool.json.JSONUtil;
import com.hwzn.util.JWTUtil;
import com.hwzn.pojo.Result;
import java.util.Objects;

/**
 * @Author: hy
 * @Date: 2023/9/6 13:12
 * @Desc: 组织
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

	@Resource
	ConfigService configService;
	
	@Resource
	UserLogService userLogService;

	@Resource
	DbTbLogService dbTbLogService;

	@Resource
	UploadRecordService uploadRecordService;

	//创建配置
	@PostMapping("/createConfig")
	public Result createConfig(@Validated @RequestBody CreateConfigDto createConfigDto, HttpServletRequest request){

		ConfigsEntity configsEntity = new ConfigsEntity();
		BeanUtils.copyProperties(createConfigDto,configsEntity);
		//创建新配置
		configService.createConfig(configsEntity);

		JSONObject data =new JSONObject();
		data.set("id",configsEntity.getId());
		if(configsEntity.getId() ==null){
			return Result.showInfo(3,"创建失败",null);
		}

		//如果配置类型是文件
		if(createConfigDto.getType()==2){
			uploadRecordService.updateUploadRecordStatus(null,createConfigDto.getContent());
		}

		//记录用户日志
		userLogService.createUserLog(CommonUtil.getUserLog("创建配置,内容为："+JSONUtil.toJsonStr(configsEntity),JWTUtil.getAccount(request.getHeader("token"))));
		//记录数据库日志
		dbTbLogService.createDbTbLog(CommonUtil.getDbTbLog("configs",configsEntity.getId(),"创建",JWTUtil.getAccount(request.getHeader("token"))));
		return Result.showInfo(0,"Success",data);
	}

	//更新配置
	@PostMapping("/updateConfig")
	public Result updateConfig(@Validated @RequestBody UpdateConfigDto updateConfigDto, HttpServletRequest request){
		//验证入参逻辑
		Result validateUpdateParam =configService.validateUpdateParam(updateConfigDto.getId());
		if(validateUpdateParam.getCode()!=0){
			return Result.showInfo(validateUpdateParam.getCode(),validateUpdateParam.getMsg(),null);
		}
		//赋值
		ConfigsEntity configsEntity = new ConfigsEntity();
		BeanUtils.copyProperties(updateConfigDto,configsEntity);

		//如果更新前的配置类型是文件
		ConfigsEntity configsEntity2=configService.getConfigsInfoByID(updateConfigDto.getId());
		if(!Objects.equals(configsEntity2.getContent(), updateConfigDto.getContent())){
			if(configsEntity2.getType()==2 ){
				uploadRecordService.updateUploadRecordStatus(configsEntity2.getContent(),null);
			}

			//如果更新后的配置类型是文件
			if(updateConfigDto.getType()==2){
				uploadRecordService.updateUploadRecordStatus(null,configsEntity2.getContent());
			}
		}
		//更新配置
		configService.updateConfig(configsEntity);
		//记录用户日志
		userLogService.createUserLog(CommonUtil.getUserLog("更新配置,内容为："+JSONUtil.toJsonStr(configsEntity),JWTUtil.getAccount(request.getHeader("token"))));
		//记录数据库日志
		dbTbLogService.createDbTbLog(CommonUtil.getDbTbLog("configs",configsEntity.getId(),"更新",JWTUtil.getAccount(request.getHeader("token"))));
		return Result.showInfo(0,"Success",null);
	}

	//删除配置
	@PostMapping("/deleteConfig")
	public Result deleteConfig(@Validated @RequestBody IdDto idDto, HttpServletRequest request){
		//验证入参逻辑
		Result validateUpdateParam =configService.validateUpdateParam(idDto.getId());
		if(validateUpdateParam.getCode()!=0){
			return Result.showInfo(validateUpdateParam.getCode(),validateUpdateParam.getMsg(),null);
		}
		//如果删除的配置类型是文件
		ConfigsEntity configsEntity=configService.getConfigsInfoByID(idDto.getId());
		if(configsEntity.getType()==2){
			uploadRecordService.updateUploadRecordStatus(configsEntity.getContent(),null);
		}
		configService.deleteConfig(idDto.getId());
		//记录用户日志
		userLogService.createUserLog(CommonUtil.getUserLog("删除配置,ID为："+ idDto.getId(),JWTUtil.getAccount(request.getHeader("token"))));
		return Result.showInfo(0,"Success", null);
	}

	//获取配置列表（分页）
	@PostMapping("/getConfigListByPage")
	public Result getConfigListByPage(@Validated @RequestBody GetConfigListByPageDto getConfigListByPageDto){
		//获取配置列表
		IPage<ConfigsEntity> resultList = configService.getButtonList(getConfigListByPageDto);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}

	//获取配置信息（Code编码）
	@PostMapping("/getConfigInfoByCode")
	public Result getConfigInfoByCode(@Validated @RequestBody CodeDto codeDto){
		//获取配置列表
		ConfigsEntity configInfo = configService.getConfigInfoByCode(codeDto.getCode());
		return Result.showInfo(0,"Success",JSONUtil.parseObj(configInfo));
	}
}
