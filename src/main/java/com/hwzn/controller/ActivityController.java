package com.hwzn.controller;

import com.hwzn.pojo.Result;
import com.hwzn.util.JWTUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.json.JSONArray;
import com.hwzn.util.CommonUtil;
import javax.annotation.Resource;
import cn.hutool.json.JSONObject;
import cn.hutool.core.date.DateUtil;
import com.hwzn.pojo.dto.common.IdDto;
import com.hwzn.service.DbTbLogService;
import com.hwzn.service.UserLogService;
import com.hwzn.service.ActivityService;
import com.hwzn.pojo.dto.common.StatusDto;
import com.hwzn.pojo.entity.ActivityEntity;
import org.springframework.beans.BeanUtils;
import com.hwzn.service.UploadRecordService;
import javax.servlet.http.HttpServletRequest;
import com.hwzn.pojo.dto.activity.CreateActivityDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.activity.UpdateActivityDto;
import com.hwzn.pojo.dto.activity.GetActivityListByPageDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: hy
 * @Date: 2023/9/6 13:12
 * @Desc: 组织
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

	@Resource
	ActivityService activityService;

	@Resource
	UserLogService userLogService;

	@Resource
	DbTbLogService dbTbLogService;

	@Resource
	UploadRecordService uploadRecordService;

	//创建活动
	@PostMapping("/createActivity")
	public Result createActivity(@Validated @RequestBody CreateActivityDto createActivityDto, HttpServletRequest request){
		ActivityEntity activityEntity = new ActivityEntity();
		BeanUtils.copyProperties(createActivityDto,activityEntity);
		//创建活动
		activityService.createActivity(activityEntity);

		JSONObject data = new JSONObject();
		data.set("id",activityEntity.getId());
		if(activityEntity.getId() == null){
			return Result.showInfo(3,"创建失败",null);
		}
		//更新上传文件状态
		uploadRecordService.updateUploadRecordStatus(null,createActivityDto.getCover());
		//更新上传文件状态（富文本）
		JSONArray newFilePathList = CommonUtil.getFilePathListByRichText(createActivityDto.getContent());
		System.out.println("newFilePathList:"+newFilePathList);
		uploadRecordService.updateUploadRecordsStatus(null,newFilePathList);

		//记录用户日志
		userLogService.createUserLog(CommonUtil.getUserLog("创建活动,内容为："+JSONUtil.toJsonStr(activityEntity),JWTUtil.getAccount(request.getHeader("token"))));
		//记录数据库日志
		dbTbLogService.createDbTbLog(CommonUtil.getDbTbLog("activitys",activityEntity.getId(),"创建",JWTUtil.getAccount(request.getHeader("token"))));
		return Result.showInfo(0,"Success",data);
	}

	//更新活动
	@PostMapping("/updateActivity")
	public Result updateActivity(@Validated @RequestBody UpdateActivityDto updateActivityDto, HttpServletRequest request){
		//先判断ID指定的活动是否存在
		ActivityEntity activityInfo = activityService.getActivityById(updateActivityDto.getId());
		if(activityInfo == null){
			return Result.showInfo(2,"指定活动不存在",null);
		}
		//赋值
		ActivityEntity activityEntity = new ActivityEntity();
		BeanUtils.copyProperties(updateActivityDto,activityEntity);

		//更新上传文件状态
		uploadRecordService.updateUploadRecordStatus(activityInfo.getCover(),updateActivityDto.getCover());
		//更新上传文件状态（富文本）
		JSONArray newFilePathList = CommonUtil.getFilePathListByRichText(updateActivityDto.getContent());
		JSONArray oldFilePathList = CommonUtil.getFilePathListByRichText(activityInfo.getContent());

		System.out.println("newFilePathList:"+newFilePathList);
		System.out.println("oldFilePathList:"+oldFilePathList);
		uploadRecordService.updateUploadRecordsStatus(oldFilePathList,newFilePathList);

		//更新活动
		activityService.updateActivity(activityEntity);
		//记录用户日志
		userLogService.createUserLog(CommonUtil.getUserLog("更新活动,内容为："+JSONUtil.toJsonStr(activityEntity),JWTUtil.getAccount(request.getHeader("token"))));
		//记录数据库日志
		dbTbLogService.createDbTbLog(CommonUtil.getDbTbLog("activitys",activityEntity.getId(),"更新",JWTUtil.getAccount(request.getHeader("token"))));
		return Result.showInfo(0,"Success",null);
	}

	//更新活动状态
	@PostMapping("/updateActivityStatus")
	public Result updateActivityStatus(@Validated @RequestBody StatusDto statusDto, HttpServletRequest request){
		//先判断ID指定的活动是否存在
		ActivityEntity activityInfo = activityService.getActivityById(statusDto.getId());
		if(activityInfo == null){
			return Result.showInfo(2,"指定活动不存在",null);
		}
		//赋值
		ActivityEntity activityEntity = new ActivityEntity();
		BeanUtils.copyProperties(statusDto,activityEntity);
		if(statusDto.getStatus()==1){
			activityEntity.setSubmitTime(DateUtil.now());
		}else {
			activityEntity.setSubmitTime(null);
		}
		//更新横幅状态
		activityService.updateActivityStatus(activityEntity);
		//记录用户日志
		userLogService.createUserLog(CommonUtil.getUserLog("更新活动状态,内容为："+JSONUtil.toJsonStr(activityEntity),JWTUtil.getAccount(request.getHeader("token"))));
		//记录数据库日志
		dbTbLogService.createDbTbLog(CommonUtil.getDbTbLog("activitys",activityEntity.getId(),statusDto.getStatus() == 1 ? "启用" : "停用",JWTUtil.getAccount(request.getHeader("token"))));
		return Result.showInfo(0,"Success",null);
	}

	//删除活动
	@PostMapping("/deleteActivity")
	public Result deleteActivity(@Validated @RequestBody IdDto idDto, HttpServletRequest request){
		//先判断ID指定的活动是否存在
		ActivityEntity activityInfo = activityService.getActivityById(idDto.getId());
		if(activityInfo == null){
			return Result.showInfo(2,"指定活动不存在",null);
		}
		//更新上传文件状态
		uploadRecordService.updateUploadRecordStatus(activityInfo.getCover(),null);
		//更新上传文件状态（富文本）
		JSONArray oldFilePathList = CommonUtil.getFilePathListByRichText(activityInfo.getContent());
		uploadRecordService.updateUploadRecordsStatus(oldFilePathList,null);

		activityService.deleteActivity(idDto.getId());
		//记录用户日志
		userLogService.createUserLog(CommonUtil.getUserLog("删除活动,ID为："+ activityInfo.getId(),JWTUtil.getAccount(request.getHeader("token"))));
		return Result.showInfo(0,"Success", null);
	}

	//获取活动列表（分页）
	@PostMapping("/getActivityListByPage")
	@Transactional
	public Result getActivityListByPage(@Validated @RequestBody GetActivityListByPageDto getActivityListByPageDto){
		//获取活动列表
		IPage<ActivityEntity> resultList = activityService.getActivityListByPage(getActivityListByPageDto);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}

	//获取活动信息（ID)
	@PostMapping("/getActivityInfoByID")
	public Result getActivityInfoByID(@Validated @RequestBody IdDto idDto){
		//获取活动列表
		ActivityEntity activityInfo = activityService.getActivityInfoByID(idDto.getId());
		if(activityInfo==null){
			return Result.showInfo(2,"指定ID数据不存在",null);
		}
		return Result.showInfo(0,"Success", JSONUtil.parseObj(activityInfo));
	}

	//读取活动信息（ID)
	@PostMapping("/readActivityInfoByID")
	public Result readActivityInfoByID(@Validated @RequestBody IdDto idDto){
		//获取活动列表
		ActivityEntity activityInfo = activityService.getActivityInfoByID(idDto.getId());
		if(activityInfo==null){
			return Result.showInfo(2,"指定ID数据不存在",null);
		}
		ActivityEntity activityEntity = new ActivityEntity();
		activityEntity.setId(idDto.getId());
		activityEntity.setReadCount(activityInfo.getReadCount()+1);//查询成功后阅读数+1
		activityService.updateActivity(activityEntity);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(activityInfo));
	}
}