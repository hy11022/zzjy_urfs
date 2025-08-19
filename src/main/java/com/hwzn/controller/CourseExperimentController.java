package com.hwzn.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.Result;
import com.hwzn.pojo.dto.common.IdDto;
import com.hwzn.pojo.dto.courseExperiment.*;
import com.hwzn.pojo.entity.CourseEntity;
import com.hwzn.pojo.entity.CourseExperimentEntity;
import com.hwzn.pojo.entity.UserEntity;
import com.hwzn.pojo.vo.DataVo;
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
import java.util.List;
import java.util.Objects;

/**
 * @Author: hy
 * @Date: 2025/7/23 13:11
 * @Desc: 课程实验中心
 */
@RestController
@RequestMapping("/courseExperiment")
public class CourseExperimentController {
	
	@Resource
	CourseExperimentService courseExperimentService;

	@Resource
	UserService userService;

	@Resource
	LogService logService;

	@Resource
	CourseService courseService;

	//筛选课程实验列表
	@PostMapping("/filterCourseExperimentList")
	public Result filterCourseExperimentList(@Validated @RequestBody FilterCourseExperimentListDto filterCourseExperimentListDto, HttpServletRequest request){
		//先判断课程是否存在
		CourseEntity courseInfo = courseService.getCourseById(filterCourseExperimentListDto.getCourseId());
		if(courseInfo == null){
			return Result.showInfo(2,"课程不存在",null);
		}

		String account = JWTUtil.getAccount(request.getHeader("token"));
		Integer role = JWTUtil.getRole(request.getHeader("token"));

		if(role != 1 && !StrUtil.equals(courseInfo.getChargerAccount(), account) && ( !courseInfo.getAssistants().contains(Objects.requireNonNull(account)) || !courseInfo.getAssistantAuthority().contains("edit-course"))){
			return Result.showInfo(2,"仅限管理员和课程负责人及含有相关权限的助教可以查看",null);
		}

		IPage<CourseExperimentEntity> resultList = courseExperimentService.filterCourseExperimentList(filterCourseExperimentListDto);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}

	//拉取课程实验（id）
	@PostMapping("/fetchCourseExperiment")
	public Result fetchCourseExperiment(@Validated @RequestBody IdDto idDto, HttpServletRequest request){
		//先判断课程实验是否存在
		CourseExperimentEntity courseExperimentInfo = courseExperimentService.getCourseExperimentInfoById(idDto.getId());
		if(courseExperimentInfo == null){
			return Result.showInfo(2,"课程实验不存在",null);
		}
		//判断课程是否存在
		CourseEntity courseInfo = courseService.getCourseByCourseExperimentId(courseExperimentInfo.getId());
		if(courseInfo == null){
			return Result.showInfo(2,"课程不存在",null);
		}

		String account = JWTUtil.getAccount(request.getHeader("token"));
		Integer role = JWTUtil.getRole(request.getHeader("token"));

		if(role != 1 && !StrUtil.equals(courseInfo.getChargerAccount(), account) && ( !courseInfo.getAssistants().contains(Objects.requireNonNull(account)) || !courseInfo.getAssistantAuthority().contains("edit-course"))){
			return Result.showInfo(2,"仅限管理员和课程负责人及含有相关权限的助教可以拉取",null);
		}
		CourseExperimentEntity courseExperimentEntity = courseExperimentService.getCourseExperimentInfoById(idDto.getId());
		if(courseExperimentEntity==null){
			return Result.showInfo(1,"数据不存在", null);
		}
		return Result.showInfo(0,"Success", JSONUtil.parseObj(courseExperimentEntity));
	}

	//创建课程实验
	@PostMapping("/createCourseExperiment")
	public  Result createCourseExperiment(@Validated @RequestBody CreateCourseExperimentDto createCourseExperimentDto, HttpServletRequest request){
		//先判断课程是否存在
		CourseEntity courseInfo = courseService.getCourseById(createCourseExperimentDto.getCourseId());
		if(courseInfo == null){
			return Result.showInfo(2,"课程不存在",null);
		}

		String account = JWTUtil.getAccount(request.getHeader("token"));
		Integer role = JWTUtil.getRole(request.getHeader("token"));

		if(role != 1 && !StrUtil.equals(courseInfo.getChargerAccount(), account) && ( !courseInfo.getAssistants().contains(Objects.requireNonNull(account)) || !courseInfo.getAssistantAuthority().contains("edit-course"))){
			return Result.showInfo(2,"仅限管理员和课程负责人及含有相关权限的助教可以创建",null);
		}
		DateTime endTime = DateUtil.parseDateTime(createCourseExperimentDto.getExperimentEndTime());
		if(DateTime.now().after(endTime)){
			return Result.showInfo(3,"课程实验的结束时间应为当前时间之后",null);
		}
		JSONObject data = new JSONObject();
		CourseExperimentEntity courseExperimentEntity = new CourseExperimentEntity();
		BeanUtils.copyProperties(createCourseExperimentDto,courseExperimentEntity);

		List<CourseExperimentEntity> courseExperimentList = courseExperimentService.getExperimentInfoByCourseId(createCourseExperimentDto.getCourseId());

		if(courseExperimentList.isEmpty()){
			courseExperimentEntity.setScoreRate(100);
		}else{
			courseExperimentEntity.setScoreRate(0);
		}

		if(courseExperimentEntity.getReportMethod()==1){
			courseExperimentEntity.setTeacherRate(100);
		}
		if(courseExperimentEntity.getReportMethod()==2){
			courseExperimentEntity.setTeacherRate(0);
		}

		if(courseExperimentEntity.getReportMethod()==3 && createCourseExperimentDto.getTeacherRate()==null){
			return Result.showInfo(1,"教师批改成绩占比不能为空", null);
		}
		if(courseExperimentService.create(courseExperimentEntity)==0){
			return Result.showInfo(1,"创建失败", null);
		}
		data.set("id",courseExperimentEntity.getId());
		//记录日志
		logService.createUserLog(account,"创建课程实验，参数为："+JSONUtil.parseObj(createCourseExperimentDto));
		logService.createDataLog(account,1,"course_experiments",courseExperimentEntity.getId(),"创建");
		return Result.showInfo(0,"Success", data);
	}

	//更新课程实验
	@PostMapping("/updateCourseExperiment")
	public Result updateCourseExperiment(@Validated @RequestBody UpdateCourseExperimentDto updateCourseExperimentDto, HttpServletRequest request){
		//先判断课程是否存在
		CourseExperimentEntity courseExperimentInfo = courseExperimentService.getCourseExperimentInfoById(updateCourseExperimentDto.getId());
		if(courseExperimentInfo == null){
			return Result.showInfo(2,"课程实验不存在",null);
		}

		CourseEntity courseInfo = courseService.getCourseById(courseExperimentInfo.getCourseId());
		if(courseInfo == null){
			return Result.showInfo(2,"课程不存在",null);
		}

		String account = JWTUtil.getAccount(request.getHeader("token"));
		Integer role = JWTUtil.getRole(request.getHeader("token"));

		if(role != 1 && !StrUtil.equals(courseInfo.getChargerAccount(), account) && ( !courseInfo.getAssistants().contains(Objects.requireNonNull(account)) || !courseInfo.getAssistantAuthority().contains("edit-course"))){
			return Result.showInfo(2,"仅限管理员和课程负责人及含有相关权限的助教可以更新",null);
		}
		DateTime endTime = DateUtil.parseDateTime(updateCourseExperimentDto.getExperimentEndTime());
		if(DateTime.now().after(endTime)){
			return Result.showInfo(3,"课程实验的结束时间应为当前时间之后",null);
		}

		CourseExperimentEntity courseExperimentEntity = new CourseExperimentEntity();
		BeanUtils.copyProperties(updateCourseExperimentDto,courseExperimentEntity);
		if(courseExperimentEntity.getReportMethod()==1){
			courseExperimentEntity.setTeacherRate(100);
		}
		if(courseExperimentEntity.getReportMethod()==2){
			courseExperimentEntity.setTeacherRate(0);
		}
		if(courseExperimentService.updateCourseExperiment(courseExperimentEntity)==0){
			return Result.showInfo(1,"更新失败", null);
		}
		//记录日志
		logService.createUserLog(JWTUtil.getAccount(request.getHeader("token")),"更新课程实验，参数为："+JSONUtil.parseObj(updateCourseExperimentDto));
		logService.createDataLog(JWTUtil.getAccount(request.getHeader("token")),1,"course_experiments",updateCourseExperimentDto.getId(),"更新");
		return Result.showInfo(0,"Success", null);
	}

	//删除课程实验
	@PostMapping("/deleteCourseExperiment")
	public Result deleteCourseExperiment(@Validated @RequestBody IdDto idDto, HttpServletRequest request){
		//先判断课程是否存在
		CourseExperimentEntity courseExperimentEntity = courseExperimentService.getCourseExperimentInfoById(idDto.getId());
		if(courseExperimentEntity == null){
			return Result.showInfo(2,"课程实验不存在",null);
		}

		CourseEntity courseInfo = courseService.getCourseById(courseExperimentEntity.getCourseId());
		String account = JWTUtil.getAccount(request.getHeader("token"));
		Integer role = JWTUtil.getRole(request.getHeader("token"));

		if(role != 1 && !StrUtil.equals(courseInfo.getChargerAccount(), account) && ( !courseInfo.getAssistants().contains(Objects.requireNonNull(account)) || !courseInfo.getAssistantAuthority().contains("edit-course"))){
			return Result.showInfo(2,"仅限管理员和课程负责人及含有相关权限的助教可以创建",null);
		}
		if(courseExperimentService.deleteById(idDto.getId())==0){
			return Result.showInfo(1,"删除失败", null);
		}
		//记录日志
		logService.createUserLog(JWTUtil.getAccount(request.getHeader("token")),"删除课程实验，ID为："+idDto.getId());
		//清除数据日志
		logService.deleteDataLogByData("course_experiments",idDto.getId());
		return Result.showInfo(0,"Success", null);
	}

	//筛选课程实验成绩占比列表
	@PostMapping("/filterCourseExperimentScoreRateList")
	public Result filterCourseExperimentScoreRateList(@Validated @RequestBody FilterCourseExperimentRateDto filterCourseExperimentRateDto, HttpServletRequest request){
		//先判断课程是否存在
		CourseEntity courseInfo = courseService.getCourseById(filterCourseExperimentRateDto.getCourseId());
		if(courseInfo == null){
			return Result.showInfo(2,"课程不存在",null);
		}
		IPage<CourseExperimentEntity> resultList = courseExperimentService.filterCourseExperimentScoreRateList(filterCourseExperimentRateDto);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}

	//筛选允许我训练的课程实验列表
	@PostMapping("/filterMyTrainCourseExperimentList")
	public Result filterMyTrainCourseExperimentList(@Validated @RequestBody FilterMyTrainCourseExperimentListDto filterMyTrainCourseExperimentListDto, HttpServletRequest request){

		String account = JWTUtil.getAccount(request.getHeader("token"));
		Integer role = JWTUtil.getRole(request.getHeader("token"));
		if(role != 3){
			return Result.showInfo(2,"仅限学生调用",null);
		}
		filterMyTrainCourseExperimentListDto.setAccount(account);
		UserEntity userEntity = userService.getUserInfoByAccount(account);
		filterMyTrainCourseExperimentListDto.setClassCode(userEntity.getClassCode());
		IPage<CourseExperimentEntity> resultList = courseExperimentService.filterMyTrainCourseExperimentList(filterMyTrainCourseExperimentListDto);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}

	//更新课程实验
	@PostMapping("/updateCourseExperimentScoreRateList")
	@Transactional
	public Result updateCourseExperimentScoreRateList(@Validated @RequestBody UpdateCourseExperimentRateDto updateCourseExperimentRateDto, HttpServletRequest request){
		List rateList = updateCourseExperimentRateDto.getRateList();
		int totalRate = 0;
		for (Object o : rateList) {
			JSONObject jo = JSONUtil.parseObj(o);
			totalRate = totalRate +Integer.parseInt(jo.getStr("scoreRate"));
		}
		if(totalRate != 100){
			return Result.showInfo(1,"成绩占比总和应为100", null);
		}
        for (Object o : rateList) {
            JSONObject jo = JSONUtil.parseObj(o);
            CourseExperimentEntity courseExperimentEntity = new CourseExperimentEntity();
            courseExperimentEntity.setId(Integer.parseInt(jo.getStr("id")));
            courseExperimentEntity.setScoreRate(Integer.parseInt(jo.getStr("scoreRate")));
			if(courseExperimentService.updateCourseExperiment(courseExperimentEntity)==0){
				return Result.showInfo(1,"更新失败", null);
			}
        }
		logService.createUserLog(JWTUtil.getAccount(request.getHeader("token")),"更新课程实验，参数为："+JSONUtil.parseObj(updateCourseExperimentRateDto));
		return Result.showInfo(0,"Success", null);
	}

	//拉取课程实验数据分析
	@PostMapping("/fetchCourseExperimentDataAnalysis")
	public Result fetchCourseExperimentDataAnalysis(@Validated @RequestBody IdDto idDto, HttpServletRequest request){
		DataVo result = courseExperimentService.fetchCourseExperimentDataAnalysis(idDto);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(result));
	}
}