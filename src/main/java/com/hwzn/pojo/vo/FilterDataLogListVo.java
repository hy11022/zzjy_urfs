package com.hwzn.pojo.vo;

import lombok.Data;

/**
 * @Author：Rongjun Du
 * @Date：2024/4/12 11:37
 * @Desc：
 */
@Data
public class FilterDataLogListVo {
	
	private Integer id;
	
	private String createrAccount;
	
	private String createTime;
	
	private Integer type;
	
	private String tableName;
	
	private Integer dataId;
	
	private String content;
	
	private String createrName;
}
