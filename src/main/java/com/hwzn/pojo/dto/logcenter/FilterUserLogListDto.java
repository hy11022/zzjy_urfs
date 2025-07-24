package com.hwzn.pojo.dto.logcenter;

import cn.hutool.json.JSONArray;
import lombok.Data;

/**
 * @Author：Rongjun Du
 * @Date：2023/11/14 17:15
 * @Desc：
 */
@Data
public class FilterUserLogListDto {
	
	private Integer pageNum = 1;
	
	private Integer pageSize = 9999;
	
	private JSONArray sortArray;
	
	private String startTime;
	
	private String endTime;
	
	private String createrAccount;
	
	private String content;
	
}
