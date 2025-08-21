package com.hwzn.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.Result;
import com.hwzn.pojo.dto.studyRecord.CreateStudyRecordDto;
import com.hwzn.pojo.dto.studyRecord.FilterStudyRecordListDto;
import com.hwzn.pojo.entity.*;
import com.hwzn.service.*;
import com.hwzn.util.JWTUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: hy
 * @Date: 2025/7/25 16:55
 * @Desc: 学习记录
 */
@RestController
@RequestMapping("/studyRecord")
public class StudyRecordController {

	@Resource
	StudyRecordService studyRecordService;

	//筛选学习记录列表
	@PostMapping("/filterStudyRecordList")
	public Result filterStudyRecordList(@Validated @RequestBody FilterStudyRecordListDto filterStudyRecordListDto){
		IPage<StudyRecordsEntity> resultList = studyRecordService.getCourseListByPage(filterStudyRecordListDto);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}


	//创建课程
	@PostMapping("/createStudyRecord")
	@Transactional
	public Result createStudyRecord(@Validated @RequestBody CreateStudyRecordDto createStudyRecordDto, HttpServletRequest request){
		String account = JWTUtil.getAccount(request.getHeader("token"));
		Integer role = JWTUtil.getRole(request.getHeader("token"));
		if (role != 3){
			return Result.showInfo(3,"仅学生角色可以调用",null);
		}
		StudyRecordsEntity studyRecordsEntity = new StudyRecordsEntity();
		BeanUtils.copyProperties(createStudyRecordDto,studyRecordsEntity);
		studyRecordsEntity.setCreaterAccount(account);
		//创建学习记录
		studyRecordService.createStudyRecord(studyRecordsEntity);
		JSONObject data = new JSONObject();
		data.set("id",studyRecordsEntity.getId());
		if(studyRecordsEntity.getId() == null){
			return Result.showInfo(4,"创建失败",null);
		}
		return Result.showInfo(0,"Success",data);
	}
}