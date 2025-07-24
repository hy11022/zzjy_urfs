package com.hwzn.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.Result;
import com.hwzn.pojo.dto.classes.CreateClassDto;
import com.hwzn.pojo.dto.classes.FilterClassListDto;
import com.hwzn.pojo.dto.classes.UpdateClassDto;
import com.hwzn.pojo.dto.common.IdDto;
import com.hwzn.pojo.entity.ClassEntity;
import com.hwzn.service.ClassService;
import com.hwzn.service.UserService;
import com.hwzn.util.CommonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * @Author：Rongjun Du
 * @Date：2024/4/15 17:00
 * @Desc：班级
 */
@RestController
@RequestMapping("/class")
public class ClassController {
	
	@Resource
	ClassService classService;
	
	@Resource
	UserService userService;

	//筛选班级列表
	@PostMapping("/filterClassList")
	public Result filterClassList(@Validated @RequestBody FilterClassListDto filterClassListDto){
		IPage<ClassEntity> resultList = classService.filterList(filterClassListDto);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}
	
	//创建班级
	@PostMapping("/admin/createClass")
	public Result createClass(@Validated @RequestBody CreateClassDto createClassDto){
		JSONObject data =new JSONObject();
		ClassEntity classEntity = new ClassEntity();
		classEntity.setName(createClassDto.getName());
		classEntity.setCode(CommonUtil.randomUUID());
		if(classService.create(classEntity)==0){
			return Result.showInfo(1,"创建失败", null);
		}
		data.set("id",classEntity.getId());
		return Result.showInfo(0,"Success",data);
	}
	
	//更新班级
	@PostMapping("/admin/updateClass")
	public Result updateClass(@Validated @RequestBody UpdateClassDto updateClassDto){
		ClassEntity classEntity = new ClassEntity();
		BeanUtils.copyProperties(updateClassDto,classEntity);
		if(classService.update(classEntity)==0){
			return Result.showInfo(1,"更新失败", null);
		}
		return Result.showInfo(0,"Success",null);
	}
	
	//删除班级
	@PostMapping("/admin/deleteClass")
	public Result deleteClass(@Validated @RequestBody IdDto idDto){
		//检测班级下辖用户
		if(!userService.countByClassId(idDto.getId()).isEmpty()){
			return Result.showInfo(2,"班级下辖用户，不可直接删除", null);
		}
		if(classService.delete(idDto.getId())==0){
			return Result.showInfo(1,"删除失败", null);
		}
		return Result.showInfo(0,"Success",null);
	}
}