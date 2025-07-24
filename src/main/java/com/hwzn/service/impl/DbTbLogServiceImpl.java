package com.hwzn.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.pojo.dto.dbTbLog.GetDbTbLogListByPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.hwzn.pojo.entity.DbTbLogEntity;
import com.hwzn.service.DbTbLogService;
import com.hwzn.mapper.DbTbLogMapper;
import cn.hutool.core.util.StrUtil;
import javax.annotation.Resource;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/4 17:28
 * @Desc: 数据库数据表日志服务实现类
 */
@Service
public class DbTbLogServiceImpl implements DbTbLogService {
	
	@Resource
	DbTbLogMapper dbTbLogMapper;

	@Override
	public void createDbTbLog(DbTbLogEntity dbTbLogEntity) {
		dbTbLogMapper.insert(dbTbLogEntity);
	}

	@Override
	public IPage<DbTbLogEntity> getDbTbLogList(GetDbTbLogListByPageDto getDbTbLogListByPageDto) {
		Page<DbTbLogEntity> page = new Page<>(getDbTbLogListByPageDto.getPageNum(),getDbTbLogListByPageDto.getPageSize());//分页
		DbTbLogEntity dbTbLogEntity = new DbTbLogEntity();
		BeanUtils.copyProperties(getDbTbLogListByPageDto,dbTbLogEntity);
		QueryWrapper<DbTbLogEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(StrUtil.isNotBlank(getDbTbLogListByPageDto.getDbName()),"db_tb_logs.db_name",getDbTbLogListByPageDto.getDbName())
				.eq(StrUtil.isNotBlank(getDbTbLogListByPageDto.getTbName()),"db_tb_logs.tb_name",getDbTbLogListByPageDto.getTbName())
				.eq(StrUtil.isNotBlank(getDbTbLogListByPageDto.getDes()),"db_tb_logs.des",getDbTbLogListByPageDto.getDes())
				.eq(StrUtil.isNotBlank(getDbTbLogListByPageDto.getCreaterAccount()),"db_tb_logs.creater_account",getDbTbLogListByPageDto.getCreaterAccount())
				.eq(getDbTbLogListByPageDto.getRId() != null,"db_tb_logs.r_id",getDbTbLogListByPageDto.getRId());
		CommonUtil.handleSortQuery(queryWrapper, getDbTbLogListByPageDto.getSortArray(), "db_tb_logs");
		return dbTbLogMapper.selectListPage(page,queryWrapper);
	}
}
