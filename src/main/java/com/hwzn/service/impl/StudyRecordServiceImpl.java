package com.hwzn.service.impl;

import javax.annotation.Resource;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.mapper.StudyRecordMapper;
import com.hwzn.pojo.dto.studyRecord.FilterStudyRecordListDto;
import com.hwzn.pojo.entity.StudyRecordsEntity;
import com.hwzn.service.StudyRecordService;
import org.springframework.stereotype.Service;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/26 15:36
 * @Desc: 课程
 */
@Service
public class StudyRecordServiceImpl implements StudyRecordService {
	
	@Resource
	private StudyRecordMapper studyRecordMapper;


	@Override
	public IPage<StudyRecordsEntity> getCourseListByPage(FilterStudyRecordListDto filterStudyRecordListDto) {
		System.out.println("filterStudyRecordListDto:"+filterStudyRecordListDto);
		Page<StudyRecordsEntity> page = new Page<>(filterStudyRecordListDto.getPageNum(),filterStudyRecordListDto.getPageSize());//分页
		QueryWrapper<StudyRecordsEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.like(StrUtil.isNotBlank(filterStudyRecordListDto.getContent()),"study_records.content",filterStudyRecordListDto.getContent())
				.eq(filterStudyRecordListDto.getDataId() != null,"study_records.data_id",filterStudyRecordListDto.getDataId())
				.eq(filterStudyRecordListDto.getType() != null,"study_records.type",filterStudyRecordListDto.getType())
				.eq(StrUtil.isNotBlank(filterStudyRecordListDto.getCreaterAccount()),"study_records.creater_account",filterStudyRecordListDto.getCreaterAccount())
				.ge(StrUtil.isNotBlank(filterStudyRecordListDto.getStartTime()),"study_records.create_time",filterStudyRecordListDto.getStartTime())
				.le(StrUtil.isNotBlank(filterStudyRecordListDto.getEndTime()),"study_records.create_time",filterStudyRecordListDto.getEndTime());
		queryWrapper.orderByDesc("study_records.create_time");
		return studyRecordMapper.getCourseListByPage(page,queryWrapper);
	}

	@Override
	public Integer createStudyRecord(StudyRecordsEntity studyRecordsEntity) {
		return studyRecordMapper.insert(studyRecordsEntity);
	}
}