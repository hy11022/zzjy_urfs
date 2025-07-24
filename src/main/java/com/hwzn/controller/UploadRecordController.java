package com.hwzn.controller;

import com.hwzn.pojo.Result;
import cn.hutool.json.JSONObject;
import javax.annotation.Resource;
import com.hwzn.pojo.dto.common.PathDto;
import org.springframework.beans.BeanUtils;
import com.hwzn.service.UploadRecordService;
import com.hwzn.pojo.dto.common.PathStatusDto;
import com.hwzn.pojo.entity.UploadRecordEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/4 13:50
 * @Desc: 上传记录
 */
@RestController
@RequestMapping("/uploadRecord")
public class UploadRecordController {
	
	@Resource
	UploadRecordService uploadRecordService;
	
	//创建上传记录
	@PostMapping("/createUploadRecord")
	public Result createUploadRecord(@Validated @RequestBody PathDto pathDto) {
		JSONObject data=new JSONObject();
		UploadRecordEntity uploadRecordEntity=new UploadRecordEntity();
		uploadRecordEntity.setPath(pathDto.getPath());
		uploadRecordService.createUploadRecord(uploadRecordEntity);
		//定义返回对象
		data.set("id",uploadRecordEntity.getId());
		return Result.showInfo(0, "Success", data);
	}
	
	//更新上传记录状态
	@PostMapping("/updateUploadRecordStatus")
	public Result updateUploadRecordStatus(@Validated @RequestBody PathStatusDto pathStatusDto) {
		UploadRecordEntity uploadRecordEntity=new UploadRecordEntity();
		BeanUtils.copyProperties(pathStatusDto,uploadRecordEntity);
		uploadRecordService.updateUploadRecordStatus(null,pathStatusDto.getPath());
		return Result.showInfo(0, "Success", null);
	}
}
