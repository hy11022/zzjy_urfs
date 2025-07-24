package com.hwzn.service;

import com.hwzn.pojo.dto.userLog.GetUserLogListByPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.entity.UserLogEntity;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:37
 * @Desc: 用户日志服务接口
 */
public interface UserLogService {
	
	void createUserLog(UserLogEntity userLogEntity);

	IPage<UserLogEntity> getUserLogListByPage(GetUserLogListByPageDto getUserLogListByPageDto);

}