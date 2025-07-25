package com.hwzn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.studyRecord.FilterStudyRecordListDto;
import com.hwzn.pojo.entity.StudyRecordsEntity;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/26 15:35
 * @Desc: 课程接口
 */
public interface StudyRecordService {

    IPage<StudyRecordsEntity> getCourseListByPage(FilterStudyRecordListDto filterStudyRecordListDto);

    Integer createStudyRecord(StudyRecordsEntity studyRecordsEntity);
}