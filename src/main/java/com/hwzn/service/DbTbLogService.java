package com.hwzn.service;

import com.hwzn.pojo.dto.dbTbLog.GetDbTbLogListByPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.entity.DbTbLogEntity;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/4 17:13
 * @Desc: 数据库数据表日志服务接口
 */
public interface DbTbLogService {
	
	//创建数据库数据表日志
	void createDbTbLog(DbTbLogEntity dbTbLogEntity);

	IPage<DbTbLogEntity> getDbTbLogList(GetDbTbLogListByPageDto getDbTbLogListByPageDto);
}
