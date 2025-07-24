package com.hwzn.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.pojo.dto.loginLog.GetLoginLogListByPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.hwzn.pojo.entity.LoginLogEntity;
import com.hwzn.service.LoginLogService;
import com.hwzn.mapper.LoginLogMapper;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import javax.annotation.Resource;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/29 10:07
 * @Desc: 登录日志服务实现类
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {
	
	@Resource
	private LoginLogMapper loginLogMapper;

	@Override
	public void createLoginLog(LoginLogEntity loginLogEntity) {
		loginLogMapper.insert(loginLogEntity);
	}

	@Override
	public IPage<LoginLogEntity> getLoginLogListByPage(GetLoginLogListByPageDto getLoginLogListByPageDto) {
		Page<LoginLogEntity> page = new Page<>(getLoginLogListByPageDto.getPageNum(),getLoginLogListByPageDto.getPageSize());//分页

		QueryWrapper<LoginLogEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.like(StrUtil.isNotBlank(getLoginLogListByPageDto.getIp()),"login_logs.ip",getLoginLogListByPageDto.getIp())
				.like(StrUtil.isNotBlank(getLoginLogListByPageDto.getCreaterAccount()),"login_logs.creater_Account",getLoginLogListByPageDto.getCreaterAccount())
				.ge(StrUtil.isNotBlank(getLoginLogListByPageDto.getStartTime()),"login_logs.create_time",getLoginLogListByPageDto.getStartTime())
				.le(StrUtil.isNotBlank(getLoginLogListByPageDto.getEndTime()),"login_logs.create_time",getLoginLogListByPageDto.getEndTime());
		CommonUtil.handleSortQuery(queryWrapper, getLoginLogListByPageDto.getSortArray(), "login_logs");
		return loginLogMapper.selectListPage(page,queryWrapper);
	}
}