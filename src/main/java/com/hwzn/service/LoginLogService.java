package com.hwzn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.loginLog.GetLoginLogListByPageDto;
import com.hwzn.pojo.entity.LoginLogEntity;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/29 10:06
 * @Desc: 登录日志服务接口
 */
public interface LoginLogService {
	void createLoginLog(LoginLogEntity loginLogEntity);

	IPage<LoginLogEntity> getLoginLogListByPage(GetLoginLogListByPageDto getLoginLogListByPageDto);
}
