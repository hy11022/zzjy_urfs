package com.hwzn.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.pojo.dto.userLog.GetUserLogListByPageDto;
import com.hwzn.pojo.entity.LoginLogEntity;
import com.hwzn.util.CommonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.hwzn.pojo.entity.UserLogEntity;
import com.hwzn.service.UserLogService;
import com.hwzn.mapper.UserLogMapper;
import javax.annotation.Resource;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:41
 * @Desc: 用户日志服务实现类
 */
@Service
public class UserLogServiceImpl implements UserLogService {
	
	@Resource
	private UserLogMapper userLogMapper;

	@Override
	public void createUserLog(UserLogEntity userLogEntity) {
		userLogMapper.insert(userLogEntity);
	}

	@Override
	public IPage<UserLogEntity> getUserLogListByPage(GetUserLogListByPageDto getUserLogListByPageDto) {
		Page<UserLogEntity> page = new Page<>(getUserLogListByPageDto.getPageNum(),getUserLogListByPageDto.getPageSize());//分页

		QueryWrapper<UserLogEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.like(StrUtil.isNotBlank(getUserLogListByPageDto.getDes()),"user_logs.des",getUserLogListByPageDto.getDes())
				.like(StrUtil.isNotBlank(getUserLogListByPageDto.getCreaterAccount()),"user_logs.creater_Account",getUserLogListByPageDto.getCreaterAccount())
				.ge(StrUtil.isNotBlank(getUserLogListByPageDto.getStartTime()),"user_logs.create_time",getUserLogListByPageDto.getStartTime())
				.le(StrUtil.isNotBlank(getUserLogListByPageDto.getEndTime()),"user_logs.create_time",getUserLogListByPageDto.getEndTime());
		CommonUtil.handleSortQuery(queryWrapper, getUserLogListByPageDto.getSortArray(), "user_logs");
		return userLogMapper.selectListPage(page,queryWrapper);
	}
}
