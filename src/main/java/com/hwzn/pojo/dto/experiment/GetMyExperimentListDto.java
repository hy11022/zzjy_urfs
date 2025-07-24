package com.hwzn.pojo.dto.experiment;

import cn.hutool.json.JSONArray;
import lombok.Data;

/**
 * @Author：Rongjun Du
 * @Date: 2023/11/29 10:55
 * @Desc：
 */
@Data
public class GetMyExperimentListDto {
	
	private Integer pageNum = 1;
	
	private Integer pageSize = 9999;

	private JSONArray sortArray;

	private String startTime;

	private String endTime;

	private String account;

	private String name;
}