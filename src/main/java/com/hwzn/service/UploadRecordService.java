package com.hwzn.service;

import cn.hutool.json.JSONArray;
import com.hwzn.pojo.entity.UploadRecordEntity;
import java.util.List;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/4 13:52
 * @Desc: 上传记录服务接口
 */
public interface UploadRecordService {
	
	//创建上传记录
	void createUploadRecord(UploadRecordEntity uploadRecordEntity);
	
	List<UploadRecordEntity> getUploadRecordByStatus(Integer status);

	void deleteUploadRecord(UploadRecordEntity uploadRecordEntity);

	void updateUploadRecordStatus(String oldFilePath,String newFilePath);

	void updateUploadRecordsStatus(JSONArray oldFilePathList, JSONArray newFilePathList);

}