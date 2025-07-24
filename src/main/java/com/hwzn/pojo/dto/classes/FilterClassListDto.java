package com.hwzn.pojo.dto.classes;

import cn.hutool.json.JSONArray;
import lombok.Data;

/**
 * @Author：Rongjun Du
 * @Date：2024/2/20 16:39
 * @Desc：
 */
@Data
public class FilterClassListDto {
	
	private Integer pageNum = 1;
	
	private Integer pageSize = 9999;
	
	private JSONArray sortArray;
	
	private String name;

	private String code;

	private Integer status;
	
}
