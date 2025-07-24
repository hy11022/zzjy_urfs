package com.hwzn.pojo.dto.activity;

import cn.hutool.json.JSONArray;
import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/23 17:07
 * @Desc: 获取横幅列表
 */
@Data
public class GetActivityListByPageDto {

	@NotNull(message = "页数不能为空")
	private Integer pageNum;

	@NotNull(message = "页码不能为空")
	private Integer pageSize;

	private String title;

	private String author;

	private Integer status;

	private String startTime;

	private String endTime;

	private JSONArray sortArray;

}