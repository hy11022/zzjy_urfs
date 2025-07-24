package com.hwzn.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.user.CreateUserByBatchDto;
import com.hwzn.pojo.dto.user.GetUserListByPageDto;
import com.hwzn.pojo.entity.ClassEntity;
import com.hwzn.pojo.entity.UserEntity;
import cn.hutool.json.JSONArray;
import com.hwzn.pojo.Result;
import com.hwzn.pojo.vo.listTeacherVo;

import java.util.List;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/22 18:36
 * @Desc: 用户服务接口
 */
public interface UserService {
	
	//通过账号获取单个用户
	UserEntity getUserInfoByAccount(String account);
	
	//创建用户
	void createUser(UserEntity userEntity);

	IPage<UserEntity> getUserList(GetUserListByPageDto getUserListByPageDto);

	Integer updateUser(UserEntity userEntity);

	void updateUserByAccount(UserEntity userEntity);

	UserEntity getUserById(Integer id);

	void deleteUser(Integer id);

	JSONArray createUserByBatch(CreateUserByBatchDto createUserByBatchDto,JSONArray dataList);

	Result validateFileParam(String excelPath);

	Result validateDataParam(JSONArray dataList);

	List<UserEntity> countByClassId(Integer classId);

	IPage<listTeacherVo> listTeacher();

	List<UserEntity> getAssisListByAccounts(String assistants);
}