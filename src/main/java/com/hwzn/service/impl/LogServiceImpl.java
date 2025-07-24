package com.hwzn.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.mapper.DataLogMapper;
import com.hwzn.mapper.LoginLogMapper;
import com.hwzn.mapper.UserLogMapper;
import com.hwzn.pojo.dto.logcenter.FilterDataLogListDto;
import com.hwzn.pojo.dto.logcenter.FilterLoginLogListDto;
import com.hwzn.pojo.dto.logcenter.FilterMyLogListDto;
import com.hwzn.pojo.dto.logcenter.FilterUserLogListDto;
import com.hwzn.pojo.entity.DataLogEntity;
import com.hwzn.pojo.entity.LoginLogEntity;
import com.hwzn.pojo.entity.UserLogEntity;
import com.hwzn.pojo.vo.FilterDataLogListVo;
import com.hwzn.pojo.vo.FilterLoginLogListVo;
import com.hwzn.pojo.vo.FilterUserLogListVo;
import com.hwzn.service.LogService;
import com.hwzn.util.CommonUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author：hy
 * @Date：2025/07/22 13:36
 * @Desc：日志服务实现类
 */
@Service
public class LogServiceImpl implements LogService {
	
	@Resource
	private LoginLogMapper loginLogMapper;
	
	@Resource
	private UserLogMapper userLogMapper;
	
	@Resource
	private DataLogMapper dataLogMapper;
	
	@Override
	public Integer createLoginLog(String createrAccount,HttpServletRequest request) {
		LoginLogEntity loginLogEntity = new LoginLogEntity();
		String ip = CommonUtil.getClientIpByRequest(request);
		UserAgent ua = UserAgentUtil.parse(request.getHeader("User-Agent"));
		loginLogEntity.setCreaterAccount(createrAccount);
		loginLogEntity.setIp(ip);
		loginLogEntity.setOs(ua.getPlatform().toString());
		loginLogEntity.setOsVersion(ua.getOs().toString());
		loginLogEntity.setBrowser(ua.getBrowser().toString());
		loginLogEntity.setBrowserVersion(ua.getVersion());
		return  loginLogMapper.insert(loginLogEntity);
	}
	
	@Override
	public IPage<FilterLoginLogListVo> filterLoginLogList(FilterLoginLogListDto filterLoginLogListDto) {
		Page<FilterLoginLogListVo> page =new Page<>(filterLoginLogListDto.getPageNum(),filterLoginLogListDto.getPageSize());
		QueryWrapper<FilterLoginLogListVo> queryWrapper = new QueryWrapper<>();
		queryWrapper.ge(StrUtil.isNotBlank(filterLoginLogListDto.getStartTime()),"login_logs.create_time",filterLoginLogListDto.getStartTime());
		queryWrapper.le(StrUtil.isNotBlank(filterLoginLogListDto.getEndTime()),"login_logs.create_time",filterLoginLogListDto.getEndTime());
		queryWrapper.eq(StrUtil.isNotBlank(filterLoginLogListDto.getCreaterAccount()), "login_logs.creater_account", filterLoginLogListDto.getCreaterAccount());
		return loginLogMapper.filterList(page,queryWrapper);
	}
	
	@Override
	public Integer createUserLog(String createrAccount,String des) {
		UserLogEntity userLogEntity = new UserLogEntity();
		userLogEntity.setCreaterAccount(createrAccount);
		userLogEntity.setDes(des);
		return userLogMapper.insert(userLogEntity);
	}
	
	@Override
	public IPage<FilterUserLogListVo> filterUserLogList(FilterUserLogListDto filterUserLogListDto) {
		Page<FilterUserLogListVo> page =new Page<>(filterUserLogListDto.getPageNum(),filterUserLogListDto.getPageSize());
		QueryWrapper<FilterUserLogListVo> queryWrapper = new QueryWrapper<>();
		CommonUtil.handleSortQuery(queryWrapper, filterUserLogListDto.getSortArray(), "user_logs");
		queryWrapper.ge(StrUtil.isNotBlank(filterUserLogListDto.getStartTime()),"user_logs.create_time",filterUserLogListDto.getStartTime());
		queryWrapper.le(StrUtil.isNotBlank(filterUserLogListDto.getEndTime()),"user_logs.create_time",filterUserLogListDto.getEndTime());
		queryWrapper.eq(StrUtil.isNotBlank(filterUserLogListDto.getCreaterAccount()), "user_logs.creater_account", filterUserLogListDto.getCreaterAccount());
		queryWrapper.like(StrUtil.isNotBlank(filterUserLogListDto.getContent()), "user_logs.content", filterUserLogListDto.getContent());
		return userLogMapper.filterList(page,queryWrapper);
	}
	
	@Override
	public IPage<UserLogEntity> filterMyLogList(FilterMyLogListDto filterMyLogListDto, String createrAccount) {
		Page<UserLogEntity> page =new Page<>(filterMyLogListDto.getPageNum(),filterMyLogListDto.getPageSize());
		QueryWrapper<UserLogEntity> queryWrapper = new QueryWrapper<>();
		CommonUtil.handleSortQuery(queryWrapper, filterMyLogListDto.getSortArray(), "user_logs");
		queryWrapper.ge(StrUtil.isNotBlank(filterMyLogListDto.getStartTime()),"user_logs.create_time",filterMyLogListDto.getStartTime());
		queryWrapper.le(StrUtil.isNotBlank(filterMyLogListDto.getEndTime()),"user_logs.create_time",filterMyLogListDto.getEndTime());
		queryWrapper.eq("user_logs.creater_account", createrAccount);
		queryWrapper.like(StrUtil.isNotBlank(filterMyLogListDto.getContent()), "user_logs.content", filterMyLogListDto.getContent());
		return userLogMapper.selectPage(page,queryWrapper);
	}
	
	@Override
	public Integer createDataLog(String createrAccount,Integer type,String tableName,Integer dataId,String content) {
		DataLogEntity dataLogEntity = new DataLogEntity();
		dataLogEntity.setCreaterAccount(createrAccount);
		dataLogEntity.setType(type);
		dataLogEntity.setTableName(tableName);
		dataLogEntity.setDataId(dataId);
		dataLogEntity.setContent(content);
		return dataLogMapper.insert(dataLogEntity);
	}
	
	@Override
	public IPage<FilterDataLogListVo> filterDataLogList(FilterDataLogListDto filterDataLogListDto) {
		Page<FilterDataLogListVo> page =new Page<>(filterDataLogListDto.getPageNum(),filterDataLogListDto.getPageSize());
		QueryWrapper<FilterDataLogListVo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(StrUtil.isNotBlank(filterDataLogListDto.getTableName()), "data_logs.table_name", filterDataLogListDto.getTableName());
		queryWrapper.eq(filterDataLogListDto.getDataId()!=null, "data_logs.data_id", filterDataLogListDto.getDataId());
		queryWrapper.like(StrUtil.isNotBlank(filterDataLogListDto.getContent()), "data_logs.content", filterDataLogListDto.getContent());
		queryWrapper.eq(StrUtil.isNotBlank(filterDataLogListDto.getCreaterAccount()), "data_logs.creater_account", filterDataLogListDto.getCreaterAccount());
		queryWrapper.ge(StrUtil.isNotBlank(filterDataLogListDto.getStartTime()),"data_logs.create_time",filterDataLogListDto.getStartTime());
		queryWrapper.le(StrUtil.isNotBlank(filterDataLogListDto.getEndTime()),"data_logs.create_time",filterDataLogListDto.getEndTime());
		CommonUtil.handleSortQuery(queryWrapper, filterDataLogListDto.getSortArray(), "data_logs");
		return dataLogMapper.filterList(page,queryWrapper);
	}
	
	@Override
	public Integer deleteDataLogByData(String tableName, Integer dataId) {
		QueryWrapper<DataLogEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(StrUtil.isNotBlank(tableName), DataLogEntity::getTableName, tableName);
		queryWrapper.lambda().eq(dataId != null, DataLogEntity::getDataId, dataId);
		return dataLogMapper.delete(queryWrapper);
	}
}
