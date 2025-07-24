package com.hwzn.controller;

import java.util.Objects;
import com.hwzn.pojo.Result;
import com.hwzn.util.JWTUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.json.JSONObject;
import javax.annotation.Resource;
import com.hwzn.service.LogService;
import com.hwzn.pojo.dto.common.IdDto;
import com.hwzn.service.ExperimentService;
import org.springframework.beans.BeanUtils;
import com.hwzn.pojo.entity.ExperimentEntity;
import javax.servlet.http.HttpServletRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.experiment.CreateExperimentDto;
import com.hwzn.pojo.dto.experiment.UpdateExperimentDto;
import com.hwzn.pojo.dto.experiment.GetMyExperimentListDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.hwzn.pojo.dto.experiment.FilterExperimentListDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: hy
 * @Date: 2025/7/23 13:11
 * @Desc: 实验中心
 */
@RestController
@RequestMapping("/experiment")
public class ExperimentController {
	
	@Resource
	ExperimentService experimentService;

	@Resource
	LogService logService;

	//筛选实验列表（管理员）
	@PostMapping("/admin/filterExperimentList")
	public Result filterExperimentList(@Validated @RequestBody FilterExperimentListDto filterExperimentListDto){
		IPage<ExperimentEntity> resultList = experimentService.filterList(filterExperimentListDto);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}

	//筛选我的虚拟仿真实验列表
	@PostMapping("/filterMyExperimentList")
	public Result filterMyExperimentList(@Validated @RequestBody GetMyExperimentListDto getMyExperimentListDto, HttpServletRequest request){
		String account = JWTUtil.getAccount(request.getHeader("token"));
		getMyExperimentListDto.setAccount(account);
		IPage<ExperimentEntity> resultList = experimentService.filterMyExperimentList(getMyExperimentListDto);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}

	//创建实验
	@PostMapping("/createExperiment")
	public  Result createExperiment(@Validated @RequestBody CreateExperimentDto createExperimentDto, HttpServletRequest request){
		String account = JWTUtil.getAccount(request.getHeader("token"));
		JSONObject data = new JSONObject();
		ExperimentEntity experimentEntity = new ExperimentEntity();
		BeanUtils.copyProperties(createExperimentDto,experimentEntity);
		experimentEntity.setCreaterAccount(account);
		if(experimentService.create(experimentEntity)==0){
			return Result.showInfo(1,"创建失败", null);
		}
		data.set("id",experimentEntity.getId());
		//记录日志
		logService.createUserLog(account,"创建实验，参数为："+JSONUtil.parseObj(createExperimentDto));
		logService.createDataLog(account,1,"experiments",experimentEntity.getId(),"创建");
		return Result.showInfo(0,"Success", data);
	}

	//更新实验
	@PostMapping("/updateExperiment")
	public Result updateExperiment(@Validated @RequestBody UpdateExperimentDto updateExperimentDto, HttpServletRequest request){
		String account = JWTUtil.getAccount(request.getHeader("token"));
		Integer role = JWTUtil.getRole(request.getHeader("token"));
		ExperimentEntity experimentInfo = experimentService.getEntityById(updateExperimentDto.getId());
		if(role != 1 && !Objects.equals(experimentInfo.getCreaterAccount(), account)){
			return Result.showInfo(1,"非管理员只能更新自己的实验", null);
		}
		ExperimentEntity experimentEntity = new ExperimentEntity();
		BeanUtils.copyProperties(updateExperimentDto,experimentEntity);
		if(experimentService.updateInfo(experimentEntity)==0){
			return Result.showInfo(1,"更新失败", null);
		}
		//记录日志
		logService.createUserLog(JWTUtil.getAccount(request.getHeader("token")),"更新实验，参数为："+JSONUtil.parseObj(updateExperimentDto));
		logService.createDataLog(JWTUtil.getAccount(request.getHeader("token")),1,"experiments",updateExperimentDto.getId(),"更新");
		return Result.showInfo(0,"Success", null);
	}

	//删除实验
	@PostMapping("/deleteExperiment")
	public Result deleteExperiment(@Validated @RequestBody IdDto idDto, HttpServletRequest request){
		String account = JWTUtil.getAccount(request.getHeader("token"));
		Integer role = JWTUtil.getRole(request.getHeader("token"));
		ExperimentEntity experimentInfo = experimentService.getEntityById(idDto.getId());
		if(role != 1 && !Objects.equals(experimentInfo.getCreaterAccount(), account)){
			return Result.showInfo(1,"非管理员只能删除自己的实验", null);
		}
		//判断实验下辖是否含有分项
//		if(itemService.countByExperimentId(idDto.getId())>0){
//			return Result.showInfo(1,"实验下辖含有分项，不可删除", null);
//		}
		if(experimentService.deleteById(idDto.getId())==0){
			return Result.showInfo(1,"删除失败", null);
		}
		//记录日志
		logService.createUserLog(JWTUtil.getAccount(request.getHeader("token")),"删除实验，ID为："+idDto.getId());
		//清除数据日志
		logService.deleteDataLogByData("experiments",idDto.getId());
		return Result.showInfo(0,"Success", null);
	}
}