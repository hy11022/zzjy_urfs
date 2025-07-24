package com.hwzn.pojo.dto.logcenter;

import lombok.Data;

/**
 * @Author：Rongjun Du
 * @Date：2023/11/14 16:47
 * @Desc：
 */
@Data
public class FilterLoginLogListDto {
	
	private Integer pageNum = 1;
	
	private Integer pageSize = 9999;
	
	private String startTime;
	
	private String endTime;
	
	private String createrAccount;
}