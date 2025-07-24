package com.hwzn.controller;

import java.util.List;
import com.hwzn.pojo.Result;
import com.hwzn.util.JWTUtil;
import cn.hutool.json.JSONUtil;
import com.hwzn.util.CommonUtil;
import javax.annotation.Resource;
import cn.hutool.json.JSONObject;
import com.hwzn.service.OrgService;
import com.hwzn.pojo.entity.OrgEntity;
import com.hwzn.service.DbTbLogService;
import com.hwzn.service.UserLogService;
import com.hwzn.pojo.dto.org.UpdateOrgDto;
import org.springframework.beans.BeanUtils;
import com.hwzn.service.UploadRecordService;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/org")
public class OrgController {

	@Resource
	OrgService orgService;

	@Resource
	UserLogService userLogService;

	@Resource
	DbTbLogService dbTbLogService;

	@Resource
	UploadRecordService uploadRecordService;

	//获取组织列表
	@PostMapping("/getOrgList")
	public Result getOrgList(){

		//获取组织列表
		List<OrgEntity> resultList = orgService.getOrgList();
		JSONObject data=new JSONObject();
		data.set("records",resultList);
		return Result.showInfo(0,"Success", data);
	}

	//更新组织
	@PostMapping("/updateOrg")
	public Result updateOrg(@Validated @RequestBody UpdateOrgDto updateOrgDto, HttpServletRequest request){

		OrgEntity orgEntity = new OrgEntity();
		String oldPath = orgService.getOrgInfoById(updateOrgDto.getId()).getLogo();
		BeanUtils.copyProperties(updateOrgDto,orgEntity);
		//更新组织
		orgService.updateOrg(orgEntity);
		//记录用户日志
		userLogService.createUserLog(CommonUtil.getUserLog("更新组织,内容为："+ JSONUtil.toJsonStr(orgEntity),JWTUtil.getAccount(request.getHeader("token"))));
		//记录数据库日志
		dbTbLogService.createDbTbLog(CommonUtil.getDbTbLog("orgs",orgEntity.getId(),"更新", JWTUtil.getAccount(request.getHeader("token"))));

		//文件有更改时，更新上传文件状态，便于后期维护去除冗余
		uploadRecordService.updateUploadRecordStatus(oldPath,updateOrgDto.getLogo());
		return Result.showInfo(0,"Success",null);
	}
}