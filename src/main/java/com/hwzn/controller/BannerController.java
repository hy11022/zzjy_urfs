package com.hwzn.controller;

import com.hwzn.pojo.Result;
import com.hwzn.util.JWTUtil;
import cn.hutool.json.JSONUtil;
import com.hwzn.util.CommonUtil;
import cn.hutool.json.JSONObject;
import javax.annotation.Resource;
import com.hwzn.pojo.dto.common.IdDto;
import com.hwzn.service.BannerService;
import com.hwzn.service.DbTbLogService;
import com.hwzn.service.UserLogService;
import com.hwzn.pojo.entity.BannerEntity;
import com.hwzn.pojo.dto.common.StatusDto;
import org.springframework.beans.BeanUtils;
import com.hwzn.service.UploadRecordService;
import javax.servlet.http.HttpServletRequest;
import com.hwzn.pojo.dto.banner.UpdateBannerDto;
import com.hwzn.pojo.dto.banner.CreateBannerDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.banner.GetBannerListByPageDto;
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
@RequestMapping("/banner")
public class BannerController {

	@Resource
	BannerService bannerService;

	@Resource
	UserLogService userLogService;

	@Resource
	DbTbLogService dbTbLogService;

	@Resource
	UploadRecordService uploadRecordService;

	//获取横幅列表（分页）
	@PostMapping("/getBannerListByPage")
	public Result getBannerListByPage(@Validated @RequestBody GetBannerListByPageDto getBannerListByPageDto){
		//获取组织列表
		IPage<BannerEntity> resultList = bannerService.getBannerListByPage(getBannerListByPageDto);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}

	//创建横幅
	@PostMapping("/createBanner")
	public Result createBanner(@Validated @RequestBody CreateBannerDto createBannerDto, HttpServletRequest request){
		BannerEntity bannerEntity = new BannerEntity();
		BeanUtils.copyProperties(createBannerDto,bannerEntity);
		//创建横幅
		bannerService.createBanner(bannerEntity);

		JSONObject data = new JSONObject();
		data.set("id",bannerEntity.getId());
		if(bannerEntity.getId() == null){
			return Result.showInfo(3,"创建失败",null);
		}
		//更新前后文件有变化,旧文件状态改为0，新文件状态设置为1
		uploadRecordService.updateUploadRecordStatus(null,createBannerDto.getImgPath());
		//记录用户日志
		userLogService.createUserLog(CommonUtil.getUserLog("创建横幅,内容为："+JSONUtil.toJsonStr(bannerEntity),JWTUtil.getAccount(request.getHeader("token"))));
		//记录数据库日志
		dbTbLogService.createDbTbLog(CommonUtil.getDbTbLog("banners",bannerEntity.getId(),"创建",JWTUtil.getAccount(request.getHeader("token"))));
		return Result.showInfo(0,"Success",data);
	}

	//更新横幅
	@PostMapping("/updateBanner")
	public Result updateBanner(@Validated @RequestBody UpdateBannerDto updateBannerDto, HttpServletRequest request){
		//先判断ID指定的横幅是否存在
		BannerEntity bannerInfo = bannerService.getBannerById(updateBannerDto.getId());
		if(bannerInfo == null){
			return Result.showInfo(2,"指定横幅不存在",null);
		}
		//更新前后文件有变化,旧文件状态改为0，新文件状态设置为1
		uploadRecordService.updateUploadRecordStatus(bannerInfo.getImgPath(),updateBannerDto.getImgPath());
		//赋值
		BannerEntity bannerEntity = new BannerEntity();
		BeanUtils.copyProperties(updateBannerDto,bannerEntity);
		//更新横幅
		bannerService.updateBanner(bannerEntity);
		//记录用户日志
		userLogService.createUserLog(CommonUtil.getUserLog("更新横幅,内容为："+JSONUtil.toJsonStr(bannerEntity),JWTUtil.getAccount(request.getHeader("token"))));
		//记录数据库日志
		dbTbLogService.createDbTbLog(CommonUtil.getDbTbLog("banners",bannerEntity.getId(),"更新",JWTUtil.getAccount(request.getHeader("token"))));
		return Result.showInfo(0,"Success",null);
	}

	//更新横幅状态
	@PostMapping("/updateBannerStatus")
	public Result updateBannerStatus(@Validated @RequestBody StatusDto statusDto, HttpServletRequest request){
		//先判断ID指定的横幅是否存在
		BannerEntity bannerInfo = bannerService.getBannerById(statusDto.getId());
		if(bannerInfo == null){
			return Result.showInfo(2,"指定横幅不存在",null);
		}
		BannerEntity bannerEntity = new BannerEntity();
		BeanUtils.copyProperties(statusDto,bannerEntity);
		//更新横幅状态
		bannerService.updateBanner(bannerEntity);
		//记录用户日志
		userLogService.createUserLog(CommonUtil.getUserLog("更新横幅状态,内容为："+JSONUtil.toJsonStr(bannerEntity),JWTUtil.getAccount(request.getHeader("token"))));
		//记录数据库日志
		dbTbLogService.createDbTbLog(CommonUtil.getDbTbLog("banners",bannerEntity.getId(),statusDto.getStatus() == 1 ? "启用" : "停用",JWTUtil.getAccount(request.getHeader("token"))));
		return Result.showInfo(0,"Success",null);
	}

	//删除横幅
	@PostMapping("/deleteBanner")
	public Result deleteBanner(@Validated @RequestBody IdDto idDto, HttpServletRequest request){
		//先判断ID指定的横幅是否存在
		BannerEntity bannerInfo = bannerService.getBannerById(idDto.getId());
		if(bannerInfo == null){
			return Result.showInfo(2,"指定横幅不存在",null);
		}
		//更新文件记录状态为0
		uploadRecordService.updateUploadRecordStatus(bannerInfo.getImgPath(),null);
		bannerService.deleteBanner(idDto.getId());
		//记录用户日志
		userLogService.createUserLog(CommonUtil.getUserLog("删除横幅,ID为："+ idDto.getId(),JWTUtil.getAccount(request.getHeader("token"))));
		return Result.showInfo(0,"Success", null);
	}
}