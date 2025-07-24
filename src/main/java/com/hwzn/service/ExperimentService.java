package com.hwzn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.experiment.FilterExperimentListDto;
import com.hwzn.pojo.dto.experiment.GetMyExperimentListDto;
import com.hwzn.pojo.entity.ExperimentEntity;
import com.hwzn.pojo.vo.ExperimentInfoVo;

/**
 * @Author：Rongjun Du
 * @Date：2023/11/29 10:54
 * @Desc：实验服务接口
 */
public interface ExperimentService {
	
	IPage<ExperimentEntity> filterList(FilterExperimentListDto filterExperimentListDto);
	
	IPage<ExperimentEntity> filterMyExperimentList(GetMyExperimentListDto getMyExperimentListDto);
	
	ExperimentEntity getEntityById(Integer id);
	
	ExperimentInfoVo getInfoById(Integer id);
	
	Integer create(ExperimentEntity experimentEntity);
	
	Integer updateInfo(ExperimentEntity experimentEntity);
	
	Integer update(ExperimentEntity experimentEntity);
	
	Integer deleteById(Integer id);
	
	Long countByProjectId(Integer projectId);
	
}
