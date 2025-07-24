package com.hwzn.controller;

import com.hwzn.pojo.Result;
import com.hwzn.util.JWTUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.json.JSONObject;
import javax.annotation.Resource;
import com.hwzn.service.DbTbLogService;
import com.hwzn.pojo.entity.DbTbLogEntity;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import com.hwzn.pojo.dto.dbTbLog.CreateDbTbLogDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.dbTbLog.GetDbTbLogListByPageDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author： hy
 * @Date： 2023/9/4 17:04
 * @Desc: 数据库数据表日志
 */
@RequestMapping("/dbTbLog")
@RestController
public class DbTbLogController {
	
	@Resource
	DbTbLogService dbTbLogService;

	//创建数据库数据表日志
	@PostMapping("/createDbTbLog")
	public Result createDbTbLog(@Validated @RequestBody CreateDbTbLogDto createDbTbLogDto, HttpServletRequest request){
		DbTbLogEntity dbTbLogEntity = new DbTbLogEntity();
		BeanUtils.copyProperties(createDbTbLogDto,dbTbLogEntity);

		dbTbLogEntity.setCreaterAccount(JWTUtil.getAccount(request.getHeader("token")));
		dbTbLogService.createDbTbLog(dbTbLogEntity);

		JSONObject data=new JSONObject();
		data.set("id",dbTbLogEntity.getId());

		return Result.showInfo(0,"Success", data);
	}

	//获取数据库数据表列表（分页）
	@PostMapping("/getDbTbLogListByPage")
	public Result getDbTbLogListByPage(@Validated @RequestBody GetDbTbLogListByPageDto getDbTbLogListByPageDto){
		//获取数据库数据表列表
		IPage<DbTbLogEntity> resultList = dbTbLogService.getDbTbLogList(getDbTbLogListByPageDto);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}
}