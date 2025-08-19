package com.hwzn.controller;

import java.util.*;
import cn.hutool.core.date.DateTime;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.hwzn.service.*;
import com.hwzn.pojo.Result;
import com.hwzn.util.JWTUtil;
import com.hwzn.pojo.entity.*;
import cn.hutool.json.JSONUtil;
import com.hwzn.pojo.dto.user.*;
import com.hwzn.util.CommonUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import javax.annotation.Resource;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import com.hwzn.pojo.vo.listTeacherVo;
import com.hwzn.pojo.dto.common.IdDto;
import cn.hutool.poi.excel.ExcelReader;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author： hy
 * @Date： 2025/7/17 13:35
 * @Desc： 用户
 */
@RequestMapping("/user")
@RestController
public class UserController {

	@Resource
	UserService userService;

	@Resource
	LogService logService;

	@Resource
	ClientService clientService;

	//筛选用户列表（管理员）
	@PostMapping("/filterUserList")
	public Result filterUserList(@Validated @RequestBody GetUserListByPageDto getUserListByPageDto){
		//获取组织列表
		IPage<UserEntity> resultList = userService.getUserList(getUserListByPageDto);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}

	//创建用户（管理员）
	@PostMapping("/admin/createUser")
	public Result createUser(@Validated @RequestBody CreateUserDto createUserDto, HttpServletRequest request){
		String account = JWTUtil.getAccount(request.getHeader("token"));
		if(createUserDto.getRole()==3 && StrUtil.isEmpty(createUserDto.getClassCode())){
			return Result.showInfo(2,"学生的班级编码不能为空",null);
		}
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(createUserDto,userEntity);
		//创建新用户
		userService.createUser(userEntity);
		JSONObject data =new JSONObject();
		data.set("id",userEntity.getId());
		//记录用户日志
		userEntity.setPassword(null);
		logService.createUserLog(account,"创建用户,内容为："+JSONUtil.toJsonStr(createUserDto));
		//记录数据库日志
		logService.createDataLog(account,1,"users",userEntity.getId(),"创建用户,内容为："+JSONUtil.toJsonStr(createUserDto));
		return Result.showInfo(0,"Success",data);
	}

	//更新用户
	@PostMapping("/admin/updateUser")
	public Result updateUser(@Validated @RequestBody UpdateUserDto updateUserDto, HttpServletRequest request){
		String account = JWTUtil.getAccount(request.getHeader("token"));
		//先判断ID指定的用户是否存在
		UserEntity userInfo = userService.getUserById(updateUserDto.getId());
		if(userInfo == null){
			return Result.showInfo(2,"指定用户不存在",null);
		}
		//赋值
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(updateUserDto,userEntity);
		//更新用户
		userService.updateUser(userEntity);
		logService.createUserLog(account,"更新用户,内容为："+JSONUtil.toJsonStr(updateUserDto));
		//记录数据库日志
		logService.createDataLog(account,1,"users",updateUserDto.getId(),"更新用户,内容为："+JSONUtil.toJsonStr(updateUserDto));
		return Result.showInfo(0,"Success",null);
	}

	//还原用户密码
	@PostMapping("/admin/restoreUserPassword")
	public Result restoreUserPassword(@Validated @RequestBody IdDto idDto, HttpServletRequest request){
		UserEntity userRole = userService.getUserInfoByAccount(JWTUtil.getAccount(request.getHeader("token")));
		if(userRole.getRole()!=1){
			return Result.showInfo(2,"非管理员用户不可还原密码", null);
		}
		UserEntity userEntity = userService.getUserById(idDto.getId());
		userEntity.setPassword("e10adc3949ba59abbe56e057f20f883e");
		if(userService.updateUser(userEntity)==0){
			return Result.showInfo(1,"还原密码失败", null);
		}
		return Result.showInfo(0,"Success",null);
	}

	//删除用户
	@PostMapping("/admin/deleteUser")
	public Result deleteUser(@Validated @RequestBody IdDto idDto, HttpServletRequest request){
		String account = JWTUtil.getAccount(request.getHeader("token"));
		//先判断ID指定的用户是否存在
		UserEntity userEntity = userService.getUserById(idDto.getId());
		if(userEntity == null){
			return Result.showInfo(2,"指定用户不存在",null);
		}
		userService.deleteUser(idDto.getId());
		//记录用户日志
		logService.createUserLog(account,"删除用户,ID为："+idDto.getId());
		return Result.showInfo(0,"Success", null);
	}

	//拉取个人信息
	@PostMapping("/fetchProfile")
	public Result fetchProfile(@Validated HttpServletRequest request){
		//获取account
		String account = JWTUtil.getAccount(request.getHeader("token"));
		System.out.println("account:"+account);
		if(account == null){
			return Result.showInfo(1,"token有误",null);
		}
		//通过账户获取单个用户
		UserEntity userEntity = userService.getUserInfoByAccount(account);
		if(userEntity==null){
			return Result.showInfo(1,"用户不存在",null);
		}
		//判断账号是否需要修改密码
		userEntity.setIsNeedUpdatePassword(userEntity.getPassword().equals("e10adc3949ba59abbe56e057f20f883e"));
		userEntity.setPassword(null);
		return Result.showInfo(0,"Success",JSONUtil.parseObj(userEntity));
	}

	//更新我的密码
	@PostMapping("/updateMyPassword")
	public Result updateMyPassword(@Validated @RequestBody UpdateUserPasswordByAccountDto updateUserPasswordByAccountDto, HttpServletRequest request){
		String account = JWTUtil.getAccount(request.getHeader("token"));
		//先判断新旧密码是否相同
		UserEntity userEntity = userService.getUserInfoByAccount(account);
		if (userEntity == null) {
			return Result.showInfo(1, "指定用户不存在", null);
		}
		if(Objects.equals(userEntity.getPassword(), updateUserPasswordByAccountDto.getPassword())){
			return Result.showInfo(3,"新密码和旧密码相同",null);
		}
		UserEntity newUserEntity = new UserEntity();
		//赋值
		newUserEntity.setPassword(updateUserPasswordByAccountDto.getPassword());
		newUserEntity.setAccount(account);
		//更新用户
		userService.updateUserByAccount(newUserEntity);
		//记录用户日志
		logService.createUserLog(account,"更新我的密码,内容为："+JSONUtil.toJsonStr(updateUserPasswordByAccountDto));
		//记录数据库日志
		logService.createDataLog(account,1,"users",userEntity.getId(),"更新我的密码,内容为："+JSONUtil.toJsonStr(updateUserPasswordByAccountDto));
		return Result.showInfo(0,"Success",null);
	}

	//登录
	@PostMapping("/login")
	public Result login(@Validated @RequestBody UserLoginDto userLoginDto, HttpServletRequest request){
		JSONObject data = new JSONObject();
		UserEntity userEntity = userService.getUserInfoByAccount(userLoginDto.getAccount());
		//账号密码校验
		if(userEntity == null || !userEntity.getPassword().equals(userLoginDto.getPassword())){
			return Result.showInfo(2,"账户或密码错误",null);
		}
		//生成token令牌，有效期12小时
		String token = JWTUtil.createToken(userLoginDto.getAccount(),userEntity.getRole(),60*12);
		data.set("token",token);
		//判断账号是否需要修改密码
		userEntity.setIsNeedUpdatePassword(userEntity.getPassword().equals("e10adc3949ba59abbe56e057f20f883e"));
		//获取用户信息
		data.set("userInfo", JSONUtil.parseObj(userEntity));
		logService.createLoginLog(userLoginDto.getAccount(),request);
		logService.createUserLog(userLoginDto.getAccount(),"登录");
		String ip = CommonUtil.getClientIpByRequest(request);
		List<ClientEntity> clientList = clientService.getRecordByIp(ip);
		if(clientList.isEmpty()){
			ClientEntity clientEntity = new ClientEntity();
			UserAgent ua = UserAgentUtil.parse(request.getHeader("User-Agent"));

			clientEntity.setIp(ip);
			clientEntity.setOs(ua.getPlatform().toString());
			clientEntity.setLastOntime(DateTime.now().toString());
			clientEntity.setCodeVersion("v1.0");
			clientEntity.setOutbreakPreventionStatus(0);
			clientEntity.setFireWallStatus(1);
			clientEntity.setStatus(1);
			clientService.recordOnline(clientEntity);
		}else{
			ClientEntity clientEntity = new ClientEntity();
			clientEntity.setId(clientList.get(0).getId());
			clientEntity.setIp(ip);
			clientEntity.setLastOntime(DateTime.now().toString());
			clientEntity.setFireWallStatus(1);
			clientEntity.setStatus(1);
			clientService.updateOnlineTime(clientEntity);
		}
		return Result.showInfo(0,"Success",data);
	}

	//退出登录
	@PostMapping("/logOut")
	public Result logOut(HttpServletRequest request){
		String ip = CommonUtil.getClientIpByRequest(request);
		List<ClientEntity> clientList = clientService.getRecordByIp(ip);
		if(!clientList.isEmpty()){
			ClientEntity clientEntity = new ClientEntity();
			clientEntity.setId(clientList.get(0).getId());
			clientEntity.setIp(ip);
			clientEntity.setLastOntime(DateTime.now().toString());
			clientEntity.setStatus(0);
			clientService.updateOnlineTime(clientEntity);
		}
		return Result.showInfo(0,"Success",null);
	}

	//罗列教师
	@PostMapping("/listTeacher")
	public Result listTeacher(@Validated HttpServletRequest request){
		IPage<listTeacherVo> resultList = userService.listTeacher();
		System.out.println("resultList:"+resultList);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}

	//批量新增用户
	@PostMapping("/createUserByBatch")
	public Result createUserByBatch(@Validated @RequestBody CreateUserByBatchDto createUserByBatchDto, HttpServletRequest request) {
		String account = JWTUtil.getAccount(request.getHeader("token"));
		//获取文件
		String excelPath = CommonUtil.urlToLocalPath(createUserByBatchDto.getFilePath());
		//验证文件
		Result validateFileParam =userService.validateFileParam(excelPath);
		if(validateFileParam.getCode()!=0){
			return Result.showInfo(validateFileParam.getCode(),validateFileParam.getMsg(),null);
		}
		//读取文件内容
		//默认UTF-8编码，可以在构造中传入第二个参数做为编码
		ExcelReader reader = ExcelUtil.getReader(excelPath);
		JSONArray dataList = JSONUtil.parseArray(reader.readAll());

		//验证EXCEL数据
		Result validateDataParam =userService.validateDataParam(dataList);
		if(validateFileParam.getCode()!=0){
			return Result.showInfo(validateDataParam.getCode(),validateDataParam.getMsg(),null);
		}
		JSONArray ids = userService.createUserByBatch(createUserByBatchDto,dataList);

		JSONObject resInfo = new JSONObject();
		resInfo.set("ids",ids);
		//记录用户日志
		logService.createUserLog(account,"批量新增用户,内容为："+JSONUtil.toJsonStr(createUserByBatchDto));
		return Result.showInfo(0, "Success", resInfo);
	}

	//验证token
	@PostMapping("/verifyToken")
	public Result verifyToken(@Validated @RequestBody TokenDto tokenDto){
		if(!JWTUtil.verify(tokenDto.getToken())){
			return Result.showInfo(401,"令牌有误或已失效",null);
		}
		return Result.showInfo(0,"Success",null);
	}
}