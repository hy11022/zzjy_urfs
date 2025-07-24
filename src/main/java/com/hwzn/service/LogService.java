package com.hwzn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.logcenter.FilterDataLogListDto;
import com.hwzn.pojo.dto.logcenter.FilterLoginLogListDto;
import com.hwzn.pojo.dto.logcenter.FilterMyLogListDto;
import com.hwzn.pojo.dto.logcenter.FilterUserLogListDto;
import com.hwzn.pojo.entity.UserLogEntity;
import com.hwzn.pojo.vo.FilterDataLogListVo;
import com.hwzn.pojo.vo.FilterLoginLogListVo;
import com.hwzn.pojo.vo.FilterUserLogListVo;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author：Rongjun Du
 * @Date：2023/11/14 14:43
 * @Desc：日志服务接口
 */
public interface LogService {
	
	Integer createLoginLog(String createrAccount,HttpServletRequest request);
	
	IPage<FilterLoginLogListVo> filterLoginLogList(FilterLoginLogListDto filterLoginLogListDto);
	
	Integer createUserLog(String createrAccount,String des);
	
	IPage<FilterUserLogListVo> filterUserLogList(FilterUserLogListDto filterUserLogListDto);
	
	IPage<UserLogEntity> filterMyLogList(FilterMyLogListDto filterMyLogListDto, String createrAccount);
	
	Integer createDataLog(String createrAccount,Integer type,String tableName,Integer dataId,String content);
	
	IPage<FilterDataLogListVo> filterDataLogList(FilterDataLogListDto filterDataLogListDto);
	
	Integer deleteDataLogByData(String tableName,Integer dataId);
	
}
