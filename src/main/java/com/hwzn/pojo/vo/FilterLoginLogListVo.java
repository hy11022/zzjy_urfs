package com.hwzn.pojo.vo;

import lombok.Data;

/**
 * @Author：Rongjun Du
 * @Date：2023/11/14 13:50
 * @Desc：
 */
@Data
public class FilterLoginLogListVo {
	
	private Integer id;
	
	private String createrAccount;
	
	private String createTime;
	
	private String ip;

	private String browser;

	private String browserVersion;

	private String os;
	
	private String osVersion;

	private String createrName;
}
