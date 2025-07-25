package com.hwzn.pojo.dto.studyRecord;

import cn.hutool.json.JSONArray;
import lombok.Data;

/**
 * @Author： hy
 * @Date： 2025/7/25 17:10
 * @Desc： 筛选学习记录列表
 */
@Data
public class FilterStudyRecordListDto {

	private Integer pageNum=1;

	private Integer pageSize=9999;

//	private JSONArray sortArray;

	private String startTime;

	private String endTime;

	private String createrAccount;

	private Integer type;

	private Integer dataId;

	private String content;

}