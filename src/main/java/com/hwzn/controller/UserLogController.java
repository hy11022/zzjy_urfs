package com.hwzn.controller;

import com.hwzn.pojo.Result;
import com.hwzn.util.JWTUtil;
import cn.hutool.json.JSONUtil;
import javax.annotation.Resource;
import cn.hutool.json.JSONObject;
import com.hwzn.pojo.dto.common.DesDto;
import com.hwzn.service.UserLogService;
import com.hwzn.pojo.entity.UserLogEntity;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.userLog.GetUserLogListByPageDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/29 10:0511
 * @Desc: 登录日志
 */
@RequestMapping("/userLog")
@RestController
public class UserLogController {
	
	@Resource
	UserLogService userLogService;
	
	//创建用户日志
	@PostMapping("/createUserLog")
	public Result createUserLog(@Validated @RequestBody DesDto desDto, HttpServletRequest request){

		UserLogEntity userLogEntity = new UserLogEntity();
		BeanUtils.copyProperties(desDto,userLogEntity);

		userLogEntity.setCreaterAccount(JWTUtil.getAccount(request.getHeader("token")));
		userLogService.createUserLog(userLogEntity);
		JSONObject data=new JSONObject();
		data.set("id",userLogEntity.getId());
		return Result.showInfo(0,"Success", data);
	}

	//获取用户日志（分页排序）
	@PostMapping("/getUserLogListByPage")
	public Result getUserLogListByPage(@Validated @RequestBody GetUserLogListByPageDto getUserLogListByPageDto){
		//取用户日志列表
		IPage<UserLogEntity> resultList = userLogService.getUserLogListByPage(getUserLogListByPageDto);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}
}