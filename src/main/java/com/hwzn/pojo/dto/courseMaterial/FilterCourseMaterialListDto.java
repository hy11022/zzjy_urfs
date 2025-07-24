package com.hwzn.pojo.dto.courseMaterial;

import cn.hutool.json.JSONArray;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author： hy
 * @Date： 2025/7/22 11:27
 * @Desc： 获取课程资料
 */
@Data
public class FilterCourseMaterialListDto {

	private Integer pageNum=1;

	private Integer pageSize=9999;

	private JSONArray sortArray;

	private String startTime;

	private String endTime;

	@NotNull(message = "课程ID不能为空")
	private Integer courseId;

	private String name;

	private String createrAccount;

	private Integer status;
}