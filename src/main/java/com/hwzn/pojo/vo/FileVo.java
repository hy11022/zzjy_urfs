package com.hwzn.pojo.vo;

import lombok.Data;

/**
 * @Author：Rongjun Du
 * @Date：2024/4/12 10:14
 * @Desc：文件
 */
@Data
public class FileVo {
	
	private String url;
	
	private String name;
	
	private String type;
	
	private String size;
	
	private Long lastModifiedTime;
	
}
