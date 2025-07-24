package com.hwzn.service.impl;

import com.hwzn.pojo.entity.LoginExceptionRecordEntity;
import com.hwzn.service.LoginExceptionRecordService;
import com.hwzn.mapper.LoginExceptionRecordMapper;
import org.springframework.stereotype.Service;
import cn.hutool.core.date.DateUtil;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:41
 * @Desc: 组织
 */
@Service
public class LoginExceptionRecordServiceImpl implements LoginExceptionRecordService {
	
	@Resource
	private LoginExceptionRecordMapper loginExceptionRecordMapper;

	@Override
	public Integer createLoginExceptionRecord(String account,Integer loginFailAccountLockDuration) {
		LoginExceptionRecordEntity loginExceptionRecordEntity = new LoginExceptionRecordEntity();
		loginExceptionRecordEntity.setCreateAccount(account);
		loginExceptionRecordEntity.setReleaseTime(DateUtil.offsetMinute(DateUtil.date(), loginFailAccountLockDuration).toString());
		loginExceptionRecordMapper.insert(loginExceptionRecordEntity);
		return loginExceptionRecordEntity.getId();
	}

	@Override
	public List<LoginExceptionRecordEntity> getLoginExceptionRecordByAccount(String account) {
		Map<String,Object> map = new HashMap<>();
		map.put("create_account",account);
		return loginExceptionRecordMapper.selectByMap(map);
	}

	@Override
	public void deleteLoginExceptionRecordByAccount(String account) {
		Map<String,Object> map = new HashMap<>();
		map.put("create_account",account);
		loginExceptionRecordMapper.deleteByMap(map);
	}
}
