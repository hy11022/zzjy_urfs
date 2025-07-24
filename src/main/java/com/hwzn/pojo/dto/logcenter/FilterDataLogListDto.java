package com.hwzn.pojo.dto.logcenter;

import cn.hutool.json.JSONArray;
import lombok.Data;

/**
 * @Author：Rongjun Du
 * @Date：2023/11/14 16:38
 * @Desc：
 */
@Data
public class FilterDataLogListDto {
	
	private Integer pageNum = 1;
	
	private Integer pageSize = 9999;
	
	private JSONArray sortArray;
	
	private String startTime;
	
	private String endTime;
	
	private String createrAccount;
	
	private Integer type;
	
	private String tableName;
	
	private Integer dataId;
	
	private String content;
	
}
