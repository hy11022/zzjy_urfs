package com.hwzn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.classes.FilterClassListDto;
import com.hwzn.pojo.entity.ClassEntity;

/**
 * @Author：Rongjun Du
 * @Date：2024/2/20 16:38
 * @Desc：班级服务接口
 */
public interface ClassService {
	
	IPage<ClassEntity> filterList(FilterClassListDto filterClassListDto);

	Integer create(ClassEntity classEntity);
	
	Integer update(ClassEntity classEntity);
	
	Integer delete(Integer id);
}
