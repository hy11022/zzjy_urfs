package com.hwzn.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONArray;
import com.hwzn.pojo.entity.UploadRecordEntity;
import com.hwzn.util.CommonUtil;
import org.springframework.stereotype.Service;
import com.hwzn.service.UploadRecordService;
import com.hwzn.mapper.UploadRecordMapper;
import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/4 13:58
 * @Desc: 上传记录服务实现类
 */
@Service
public class UploadRecordServiceImpl implements UploadRecordService {
	
	@Resource
	private UploadRecordMapper uploadRecordMapper;
	
	@Override
	public void createUploadRecord(UploadRecordEntity uploadRecordEntity) {
		uploadRecordMapper.insert(uploadRecordEntity);
	}
	
	@Override
	public List<UploadRecordEntity> getUploadRecordByStatus(Integer status) {
		Map<String, Object> map = new HashMap<>();
		map.put("status",status);
		return uploadRecordMapper.selectByMap(map);
	}

	@Override
	public void deleteUploadRecord(UploadRecordEntity uploadRecordEntity) {
		uploadRecordMapper.deleteById(uploadRecordEntity);
	}

	@Override
	public void updateUploadRecordStatus(String oldFilePath, String newFilePath) {
		if(oldFilePath !=null && oldFilePath.equals(newFilePath)){
			return;
		}
		if(newFilePath !=null){
			uploadRecordMapper.updateStatusByPath(newFilePath,1);
		}
		if(oldFilePath !=null){
			uploadRecordMapper.updateStatusByPath(oldFilePath,0);
		}
	}

	@Override
	public void updateUploadRecordsStatus(JSONArray oldFilePathList, JSONArray newFilePathList) {
		if(oldFilePathList!=null && newFilePathList != null){
			if (new HashSet<>(oldFilePathList).containsAll(newFilePathList) && new HashSet<>(newFilePathList).containsAll(oldFilePathList)){
				return;
			}
		}

		if(!CollUtil.isEmpty(oldFilePathList)){
			//取差集，从oldFilePathList中扣除newFilePathList
			List<Object> oldList= CommonUtil.removeMixedList(oldFilePathList,newFilePathList);
			for (Object o:oldList){
				uploadRecordMapper.updateStatusByPath(o.toString(),0);
			}
		}

		if(!CollUtil.isEmpty(newFilePathList)){
			//取差集，从newFilePathList中扣除oldFilePathList
			List<Object> newList= CommonUtil.removeMixedList(newFilePathList,oldFilePathList);
			for (Object o:newList){
				uploadRecordMapper.updateStatusByPath(o.toString(),1);
			}
		}
	}
}
