package com.hwzn.controller;

import java.io.IOException;
import com.hwzn.pojo.Result;
import com.hwzn.util.JWTUtil;
import cn.hutool.json.JSONObject;
import javax.annotation.Resource;
import com.hwzn.service.UploadService;
import com.hwzn.service.UserLogService;
import com.hwzn.pojo.entity.UserLogEntity;
import com.hwzn.service.UploadRecordService;
import javax.servlet.http.HttpServletRequest;
import com.hwzn.pojo.entity.UploadRecordEntity;
import org.springframework.web.bind.annotation.*;
import com.hwzn.pojo.dto.upload.UploadByBase64Dto;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.validation.annotation.Validated;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/25 14:46
 * @Desc: 上传
 */
@RestController
@RequestMapping("/upload")
public class UploadController {
	
	@Resource
	UploadService uploadService;
	
	@Resource
	UploadRecordService uploadRecordService;

	@Resource
	UserLogService userLogService;

	//上传（文件流）
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/uploadByFile")
	public Result uploadByFile(MultipartFile file) throws IOException {
		String url = uploadService.uploadByFile(file);
		//定义返回对象
		JSONObject data = new JSONObject();
		data.set("url",url);
		return Result.showInfo(0, "Success", data);
	}
	
	//上传（Base64）
	@PostMapping("/uploadByBase64")
	public Result uploadByBase64(@Validated @RequestBody UploadByBase64Dto uploadByBase64Dto, HttpServletRequest request) {
		//上传文件（Base64）到本地并返回网路路径
		String url = uploadService.uploadByBase64(uploadByBase64Dto);
		
		//创建上传记录
		UploadRecordEntity uploadRecordEntity=new UploadRecordEntity();
		uploadRecordEntity.setPath(url);
		uploadRecordService.createUploadRecord(uploadRecordEntity);
		
		//记录用户日志
		UserLogEntity userLogEntity = new UserLogEntity();
		userLogEntity.setCreaterAccount(JWTUtil.getAccount(request.getHeader("token")));
		userLogEntity.setDes("上传（Base64）新内容，地址为:"+url);
		userLogService.createUserLog(userLogEntity);
		//定义返回对象
		JSONObject data = new JSONObject();
		data.set("url",url);
		return Result.showInfo(0, "Success", data);
	}

	//上传（切片）
	@RequestMapping(value = "/uploadByChunk")
	public String uploadByChunk(HttpServletRequest request) {
		return uploadService.uploadByChunk(null,request);
	}
}