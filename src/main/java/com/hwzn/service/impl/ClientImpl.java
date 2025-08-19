package com.hwzn.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.client.FilterClientDto;
import com.hwzn.pojo.entity.LoginLogEntity;
import org.springframework.stereotype.Service;
import com.hwzn.pojo.entity.ClientEntity;
import com.hwzn.service.ClientService;
import com.hwzn.mapper.ClientMapper;
import cn.hutool.core.util.StrUtil;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.hwzn.util.CommonUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: hy
 * @Date: 2025/8/6 14:09
 * @Desc: 终端管理
 */
@Service
public class ClientImpl implements ClientService {
	
	@Resource
	private ClientMapper clientMapper;

	@Override
	public IPage<ClientEntity> filterClientList(FilterClientDto filterClientDto) {
		Page<ClientEntity> page = new Page<>(filterClientDto.getPageNum(),filterClientDto.getPageSize());//分页

		QueryWrapper<ClientEntity> queryWrapper = new QueryWrapper<>();
		CommonUtil.handleSortQuery(queryWrapper, filterClientDto.getSortArray(), "client");
		queryWrapper.eq(StrUtil.isNotBlank(filterClientDto.getIp()),"ip",filterClientDto.getIp())
				.eq(StrUtil.isNotBlank(filterClientDto.getOs()),"os",filterClientDto.getOs())
				.eq(StrUtil.isNotBlank(filterClientDto.getProcessor()),"processor",filterClientDto.getProcessor())
				.eq(StrUtil.isNotBlank(filterClientDto.getCodeVersion()),"code_version",filterClientDto.getCodeVersion())
				.eq(filterClientDto.getStatus() != null,"status",filterClientDto.getStatus())
				.eq(filterClientDto.getFireWallStatus() != null,"fire_wall_status",filterClientDto.getFireWallStatus())
				.eq(filterClientDto.getOutbreakPreventionStatus() != null,"outbreak_prevention_status",filterClientDto.getOutbreakPreventionStatus());
		return clientMapper.selectPage(page,queryWrapper);
	}

	@Override
	public List<ClientEntity> getRecordByIp(String ip) {
		Map map = new HashMap<>();
		map.put("ip",ip);
		return clientMapper.selectByMap(map);
	}

	@Override
	public Integer recordOnline(ClientEntity clientEntity) {
		return  clientMapper.insert(clientEntity);
	}

	@Override
	public Integer updateOnlineTime(ClientEntity clientEntity) {
		return clientMapper.updateById(clientEntity);
	}
}