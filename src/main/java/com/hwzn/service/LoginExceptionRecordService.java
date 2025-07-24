package com.hwzn.service;

import com.hwzn.pojo.entity.LoginExceptionRecordEntity;
import java.util.List;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:37
 * @Desc: 错误日志记录接口
 */
public interface LoginExceptionRecordService {

    Integer createLoginExceptionRecord(String account,Integer loginFailAccountLockDuration);

    List<LoginExceptionRecordEntity> getLoginExceptionRecordByAccount(String account);

    void deleteLoginExceptionRecordByAccount(String account);
}