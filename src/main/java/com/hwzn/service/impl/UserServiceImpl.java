package com.hwzn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.user.GetUserListByPageDto;
import com.hwzn.pojo.dto.user.CreateUserByBatchDto;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import cn.hutool.core.collection.CollUtil;
import com.hwzn.pojo.entity.UserEntity;
import com.hwzn.pojo.vo.listTeacherVo;
import com.hwzn.service.UserService;
import cn.hutool.core.util.StrUtil;
import com.hwzn.mapper.UserMapper;
import cn.hutool.json.JSONObject;
import javax.annotation.Resource;
import cn.hutool.json.JSONArray;
import com.hwzn.util.CommonUtil;
import cn.hutool.json.JSONUtil;
import com.hwzn.pojo.Result;
import java.io.File;
import java.util.*;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/30 10:03
 * @Desc: 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserMapper userMapper;
	
	//创建用户
	@Override
	public void createUser(UserEntity userEntity) {
		userMapper.insert(userEntity);
	}

	@Override
	public IPage<UserEntity> getUserList(GetUserListByPageDto getUserListByPageDto) {
		Page<UserEntity> page = new Page<>(getUserListByPageDto.getPageNum(),getUserListByPageDto.getPageSize());//分页

		QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
		CommonUtil.handleSortQuery(queryWrapper, getUserListByPageDto.getSortArray(), "users");
		queryWrapper.like(StrUtil.isNotBlank(getUserListByPageDto.getAccount()),"users.account",getUserListByPageDto.getAccount())
				.eq(StrUtil.isNotBlank(getUserListByPageDto.getClassCode()),"users.class_code",getUserListByPageDto.getClassCode())
				.eq(getUserListByPageDto.getRole() != null,"users.role",getUserListByPageDto.getRole())
				.like(StrUtil.isNotBlank(getUserListByPageDto.getName()),"users.name",getUserListByPageDto.getName());
		return userMapper.selectListPage(page,queryWrapper);
	}

	@Override
	public Integer updateUser(UserEntity userEntity) {
		return userMapper.updateById(userEntity);
	}

	@Override
	public void updateUserByAccount(UserEntity userEntity) {
		QueryWrapper<UserEntity> updateWrapper = new QueryWrapper<>();
		updateWrapper.eq("account",userEntity.getAccount());
		userMapper.update(userEntity,updateWrapper);
	}

	@Override
	public UserEntity getUserById(Integer id) {
		return userMapper.selectById(id);
	}

	@Override
	public void deleteUser(Integer id) {
		userMapper.deleteById(id);
	}

	//批量新增用户
	@Transactional
	public JSONArray createUserByBatch(CreateUserByBatchDto createUserByBatchDto,JSONArray dataList) {
		//组织数据
		List<UserEntity> users = new ArrayList<>();
		for (Object o : dataList) {
			JSONObject userJo = JSONUtil.parseObj(o);
			UserEntity userEntity = new UserEntity();
			BeanUtils.copyProperties(createUserByBatchDto, userEntity);
			userEntity.setAccount(userJo.get("账户").toString());
			userEntity.setName(userJo.get("姓名").toString());
			userEntity.setPassword("e10adc3949ba59abbe56e057f20f883e");//md5加密123456
			users.add(userEntity);
		}
		return batchAddUser(users,1000);
	}

	@Override
	public Result validateFileParam(String excelPath) {
		File file = new File(excelPath);
		if(!file.exists()){
			return Result.showInfo(2, "文件不存在", null);
		}
		String type = CommonUtil.getLastPointStr(excelPath,"after");
		if(!type.equals("xlsx")){
			return Result.showInfo(3, "不支持该文件类型", null);
		}
		return Result.showInfo(0, "Success", null);
	}

	@Override
	public Result validateDataParam(JSONArray dataList) {
		String strr = findErrorLineIndex(dataList);//查重
		if (!StrUtil.isEmpty(strr)) {
			return Result.showInfo(4, strr, null);
		}
		String accountRegexp="[a-zA-Z0-9_]{4,19}$";
		String nameRegexp="^[a-zA-Z0-9\\u4e00-\\u9fa5]+${0,19}";

		StringBuilder str = new StringBuilder();
		for (int j = 0;j < dataList.size(); j++) {
			JSONObject jo = JSONUtil.parseObj(dataList.get(j));
			if(!jo.get("账户").toString().matches(accountRegexp)){
				str.append("第").append(j + 2).append("行账号格式有误，允许5-20字符，允许字母数字下划线;");//+2,第一行是标题，数据从0取，出错位置在excel中得i加2
				return Result.showInfo(5, str.toString(), null);
			}
			if (!jo.get("姓名").toString().matches(nameRegexp)) {
				str.append("第").append(j + 2).append("行用户名格式有误，允许1-20字符，允许中英文数字;");
				return Result.showInfo(6, str.toString(), null);
			}
		}
		return Result.showInfo(0, "Success", null);
	}

	public static String findErrorLineIndex(JSONArray ja) {//查重，返回第一个重复位置
		HashSet<Object> hs = new HashSet<>();
		StringBuilder str = new StringBuilder();
		for (int i=0;i<ja.size();i++){
			JSONObject jo = JSONUtil.parseObj(ja.get(i));
			if(!hs.add(jo.get("账户"))){
				str.append("第").append(i+2).append("行出错,数据重复");
				return str.toString();
			}
		}
		return str.toString();
	}

	//批量插入
	public JSONArray batchAddUser(Collection<UserEntity> entityList, int batchSize) {
		JSONArray ids = new JSONArray();
		int size = entityList.size();
		int idxLimit = Math.min(batchSize, size);
		int i = 1;
		//保存单批提交的数据集合
		List<UserEntity> oneBatchList = new ArrayList<>();
		for (Iterator<UserEntity> userVar = entityList.iterator(); userVar.hasNext(); ++i) {
			UserEntity element = userVar.next();
			oneBatchList.add(element);
			if (i == idxLimit) {
				userMapper.insertBatchSomeColumn(oneBatchList);
				for (UserEntity userEntity : oneBatchList) {
					ids.add(userEntity.getId());	//收集id
				}
				//每次提交后需要清空集合数据
				oneBatchList.clear();
				idxLimit = Math.min(idxLimit + batchSize, size);
			}
		}
		return ids;
	}

	//通过账号获取单个用户
	@Override
	public UserEntity getUserInfoByAccount(String account) {
		QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("users.account",account);
		List<UserEntity> user = userMapper.getUserInfoByAccount(queryWrapper);
		if (!CollUtil.isEmpty(user)){
			return user.get(0);
		}
		return null;
	}

	@Override
	public List<UserEntity> countByClassId(Integer classId) {
		QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("classes.id", classId);
		return userMapper.countByClassId(queryWrapper);
	}

	@Override
	public IPage<listTeacherVo> listTeacher() {
		Page<listTeacherVo> page = new Page<>(1,9999);//分页
		QueryWrapper<listTeacherVo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("users.role",2);
		return userMapper.listTeacher(page,queryWrapper);
	}

	@Override
	public List<UserEntity> getAssisListByAccounts(String assistants) {
		return userMapper.getAssisListByAccounts(assistants);
	}
}
