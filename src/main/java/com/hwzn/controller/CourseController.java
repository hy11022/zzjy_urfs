package com.hwzn.controller;

import java.util.List;
import java.util.Objects;
import com.hwzn.service.*;
import com.hwzn.pojo.Result;
import com.hwzn.util.JWTUtil;
import com.hwzn.pojo.entity.*;
import cn.hutool.json.JSONUtil;
import cn.hutool.json.JSONObject;
import javax.annotation.Resource;
import com.hwzn.pojo.dto.course.*;
import cn.hutool.core.util.StrUtil;
import com.hwzn.pojo.vo.CourseInfoVo;
import com.hwzn.pojo.vo.CourseTermVo;
import com.hwzn.pojo.dto.common.IdDto;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: hy
 * @Date: 2025/7/16 10:27
 * @Desc: 课程
 */
@RestController
@RequestMapping("/course")
public class CourseController {

	@Resource
	CourseService courseService;

	@Resource
	UserService userService;

	@Resource
	CourseTermService courseTermService;

	@Resource
	CourseStudentService courseStudentService;

	@Resource
	CourseMaterialService courseMaterialService;

	@Resource
	CourseExperimentService courseExperimentService;

	//筛选课程列表
	@PostMapping("/filterCourseList")
	public Result filterCourseList(@Validated @RequestBody GetCourseListByPageDto getCourseListByPageDto){
		//获取课程列表
		IPage<CourseEntity> resultList = courseService.getCourseListByPage(getCourseListByPageDto);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}

	//筛选我管理的课程
	@PostMapping("/filterMyManagingCourseList")
	public Result filterMyManagingCourseList(@Validated @RequestBody GetCourseListByPageDto getCourseListByPageDto, HttpServletRequest request){
		//获取课程列表
		String account = JWTUtil.getAccount(request.getHeader("token"));
		getCourseListByPageDto.setAccount(account);
		IPage<CourseEntity> resultList = courseService.filterMyManagingCourseList(getCourseListByPageDto);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}

	//创建课程
	@PostMapping("/admin/createCourse")
	@Transactional
	public Result createCourse(@Validated @RequestBody CreateCourseDto createCourseDto){
		UserEntity userEntity = userService.getUserInfoByAccount(createCourseDto.getChargerAccount());
		if (userEntity==null){
			return Result.showInfo(3,"负责人账户不存在",null);
		}

		if(StrUtil.split(createCourseDto.getAssistants(),",").contains(createCourseDto.getChargerAccount())){
			return Result.showInfo(3,"助教不可包含负责人", null);
		}

		CourseEntity courseEntity = new CourseEntity();
		BeanUtils.copyProperties(createCourseDto,courseEntity);
		//创建课程
		courseService.createCourse(courseEntity);
		JSONObject data = new JSONObject();
		data.set("id",courseEntity.getId());
		if(courseEntity.getId() == null){
			return Result.showInfo(4,"创建失败",null);
		}
		return Result.showInfo(0,"Success",data);
	}

	//更新课程
	@PostMapping("/updateCourse")
	@Transactional
	public Result updateCourse(@Validated @RequestBody UpdateCourseDto updateCourseDto, HttpServletRequest request){
		//先判断ID指定的数据是否存在
		CourseEntity courseInfo = courseService.getCourseById(updateCourseDto.getId());
		if(courseInfo == null){
			return Result.showInfo(2,"指定数据不存在",null);
		}
		String account = JWTUtil.getAccount(request.getHeader("token"));
		int role = JWTUtil.getRole(request.getHeader("token"));

		if(role != 1 && !StrUtil.equals(courseInfo.getChargerAccount(), account) && ( !courseInfo.getAssistants().contains(Objects.requireNonNull(account)) || !courseInfo.getAssistantAuthority().contains("edit-course"))){
			return Result.showInfo(2,"仅限管理员和课程负责人及含有相关权限的助教可以更新",null);
		}
		if(role != 1 && !Objects.equals(updateCourseDto.getChargerAccount(), courseInfo.getChargerAccount())){
			return Result.showInfo(2,"非管理员不可修改负责人",null);
		}
		if(courseInfo.getAssistants().contains(Objects.requireNonNull(account)) && !Objects.equals(updateCourseDto.getAssistants(), courseInfo.getAssistants())){
			return Result.showInfo(2,"助教不可修改助教",null);
		}

		//赋值
		CourseEntity courseEntity = new CourseEntity();
		BeanUtils.copyProperties(updateCourseDto,courseEntity);
		//更新课程
		courseService.updateCourse(courseEntity);
		return Result.showInfo(0,"Success",null);
	}

	//删除课程
	@PostMapping("/admin/deleteCourse")
	@Transactional
	public Result deleteCourse(@Validated @RequestBody IdDto idDto){
		courseService.deleteCourse(idDto.getId());//删除课程
		return Result.showInfo(0,"Success", null);
	}

	//拉取课程
	@PostMapping("/fetchCourse")
	public Result fetchCourse(@Validated @RequestBody IdDto idDto, HttpServletRequest request){
		//获取活动列表
		CourseEntity courseInfo = courseService.getCourseById(idDto.getId());
		if(courseInfo==null){
			return Result.showInfo(2,"指定ID数据不存在",null);
		}
		//只有管理员和课程负责人及助教可以拉取
		if(JWTUtil.getRole(request.getHeader("token"))!=1 && !Objects.equals(courseInfo.getChargerAccount(), JWTUtil.getAccount(request.getHeader("token"))) && !StrUtil.split(courseInfo.getAssistants(),",").contains(JWTUtil.getAccount(request.getHeader("token")))){
			return Result.showInfo(2,"权限不足", null);
		}
		return Result.showInfo(0,"Success", JSONUtil.parseObj(courseInfo));
	}

	//拉取前台课程信息
	@PostMapping("/fetchClientCourseInfo")
	public Result fetchClientCourseInfo(@Validated @RequestBody IdDto idDto, HttpServletRequest request){
		//获取活动列表
		CourseEntity courseInfo = courseService.getCourseInfoById(idDto.getId());
		CourseInfoVo courseInfoVo = new CourseInfoVo();//存储最终返回值
		BeanUtils.copyProperties(courseInfo,courseInfoVo);
		courseInfoVo.setAssistants(courseInfo.getAssistants());
		List<UserEntity> userEntity = userService.getAssisListByAccounts(courseInfo.getAssistants());
		courseInfoVo.setAssistantList(userEntity);

		List<CourseTermEntity> courseTermEntity = courseTermService.getCurrentTermInfo(idDto.getId());//该课程查询当前期次
		CourseStudentEntity courseStudentEntity = new CourseStudentEntity();
		if(!courseTermEntity.isEmpty()){
			CourseTermVo courseTermVo = new CourseTermVo();
			BeanUtils.copyProperties(courseTermEntity.get(0),courseTermVo);
			courseInfoVo.setCurrentTermInfo(courseTermVo);//课程学期
			courseStudentEntity.setTermId(courseTermEntity.get(0).getId());
		}
		courseStudentEntity.setCourseId(idDto.getId());
		String account = JWTUtil.getAccount(request.getHeader("token"));
		courseStudentEntity.setAccount(account);

		List<CourseStudentEntity> entityList = courseStudentService.getCourseStudentForCurrent(courseStudentEntity);
        courseInfoVo.setJoined(!entityList.isEmpty());//是否加入课程

		List<CourseMaterialsEntity> materialList = courseMaterialService.getCourseMaterialByCourse(idDto.getId());
		courseInfoVo.setMaterialList(materialList);//课程资料

		List<CourseExperimentEntity> courseExperimentList = courseExperimentService.getExperimentInfoByCourseId(idDto.getId());
		courseInfoVo.setExperimentList(courseExperimentList);//课程实验
        return Result.showInfo(0,"Success", JSONUtil.parseObj(courseInfoVo));
	}

	//筛选我负责的课程列表
	@PostMapping("/filterMyChargingCourseList")
	public Result filterMyChargingCourseList(@Validated @RequestBody FilterMyChargingCourseListDto filterMyChargingCourseListDto, HttpServletRequest request){
		String account = JWTUtil.getAccount(request.getHeader("token"));
		if(StrUtil.isEmpty(account)){
			return Result.showInfo(2,"用户账户出错",null);
		}
		filterMyChargingCourseListDto.setAccount(account);
		IPage<CourseEntity> resultList = courseService.filterMyChargingCourseList(filterMyChargingCourseListDto);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}

	//更新课程角色权限
	@PostMapping("/updateCourseRoleAuthority")
	@Transactional
	public Result updateCourseRoleAuthority(@Validated @RequestBody UpdateCourseRoleAuDto updateCourseRoleAuDto, HttpServletRequest request){
		//先判断ID指定的数据是否存在
		CourseEntity courseInfo = courseService.getCourseById(updateCourseRoleAuDto.getId());
		if(courseInfo == null){
			return Result.showInfo(2,"指定数据不存在",null);
		}
		String account = JWTUtil.getAccount(request.getHeader("token"));
		Integer role = JWTUtil.getRole(request.getHeader("token"));

		if(role != 1 && !StrUtil.equals(courseInfo.getChargerAccount(), account)){
			return Result.showInfo(2,"仅限管理员和课程负责人更新",null);
		}
		//赋值
		CourseEntity courseEntity = new CourseEntity();
		BeanUtils.copyProperties(updateCourseRoleAuDto,courseEntity);
		//更新课程
		courseService.updateCourse(courseEntity);
		return Result.showInfo(0,"Success",null);
	}

	//筛选我参加过的课程列表
	@PostMapping("/filterMyJoinedCourseList")
	public Result filterMyJoinedCourseList(@Validated @RequestBody FilterMyJoinedCourseListDto filterMyJoinedCourseListDto, HttpServletRequest request){
		String account = JWTUtil.getAccount(request.getHeader("token"));
		Integer role = JWTUtil.getRole(request.getHeader("token"));
		if(role != 3){
			return Result.showInfo(2,"仅学生可使用",null);
		}
		filterMyJoinedCourseListDto.setAccount(account);
		IPage<CourseEntity> resultList = courseService.filterMyJoinedCourseList(filterMyJoinedCourseListDto);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}

}