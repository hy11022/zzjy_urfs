package com.hwzn.controller;

import com.hwzn.pojo.Result;
import com.hwzn.util.JWTUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.json.JSONObject;
import javax.annotation.Resource;
import com.hwzn.service.LoginLogService;
import com.hwzn.pojo.entity.LoginLogEntity;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import com.hwzn.pojo.dto.loginLog.CreateLoginLogDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.loginLog.GetLoginLogListByPageDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/29 10:05
 * @Desc: 登录日志
 */
@RequestMapping("/loginLog")
@RestController
public class LoginLogController {

	@Resource
	LoginLogService loginLogService;

	//创建登录日志
	@PostMapping("/createLoginLog")
	public Result createLoginLog(@Validated @RequestBody CreateLoginLogDto createLoginLogDto, HttpServletRequest request){
		LoginLogEntity loginLogEntity = new LoginLogEntity();
		BeanUtils.copyProperties(createLoginLogDto,loginLogEntity);
		loginLogEntity.setCreaterAccount(JWTUtil.getAccount(request.getHeader("token")));
		loginLogService.createLoginLog(loginLogEntity);
		JSONObject data=new JSONObject();
		data.set("id",loginLogEntity.getId());
		return Result.showInfo(0,"Success", data);
	}

	//获取登录日志(分页排序)
	@PostMapping("/getLoginLogListByPage")
	public Result getLoginLogListByPage(@Validated @RequestBody GetLoginLogListByPageDto getLoginLogListByPageDto){
		//取用户日志列表
		IPage<LoginLogEntity> resultList = loginLogService.getLoginLogListByPage(getLoginLogListByPageDto);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}
}
