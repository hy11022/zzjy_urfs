package com.hwzn.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.Result;
import com.hwzn.pojo.dto.logcenter.FilterDataLogListDto;
import com.hwzn.pojo.dto.logcenter.FilterLoginLogListDto;
import com.hwzn.pojo.dto.logcenter.FilterMyLogListDto;
import com.hwzn.pojo.dto.logcenter.FilterUserLogListDto;
import com.hwzn.pojo.entity.UserLogEntity;
import com.hwzn.pojo.vo.FilterDataLogListVo;
import com.hwzn.pojo.vo.FilterLoginLogListVo;
import com.hwzn.pojo.vo.FilterUserLogListVo;
import com.hwzn.service.LogService;
import com.hwzn.util.JWTUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author：Rongjun Du
 * @Date：2024/2/19 15:15
 * @Desc：日志
 */
@RestController
@RequestMapping("/log")
public class LogController {
	
	@Resource
	LogService logService;
	
	//筛选登录日志列表
	@PostMapping("/admin/filterLoginLogList")
	public Result filterLoginLogList(@Validated @RequestBody FilterLoginLogListDto filterLoginLogListDto){
		IPage<FilterLoginLogListVo> resultList = logService.filterLoginLogList(filterLoginLogListDto);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}
	
	//筛选用户日志列表
	@PostMapping("/admin/filterUserLogList")
	public Result filterUserLogList(@Validated @RequestBody FilterUserLogListDto filterUserLogListDto){
		IPage<FilterUserLogListVo> resultList = logService.filterUserLogList(filterUserLogListDto);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}
	
	//筛选我的日志列表
	@PostMapping("/filterMyLogList")
	public Result filterMyLogList(@Validated @RequestBody FilterMyLogListDto filterMyLogListDto, HttpServletRequest request){
		IPage<UserLogEntity> resultList = logService.filterMyLogList(filterMyLogListDto, JWTUtil.getAccount(request.getHeader("token")));
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}
	
	//筛选数据日志列表
	@PostMapping("/admin/filterDataLogList")
	public Result filterDataLogList(@Validated @RequestBody FilterDataLogListDto filterDataLogListDto){
		IPage<FilterDataLogListVo> resultList = logService.filterDataLogList(filterDataLogListDto);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}
}
