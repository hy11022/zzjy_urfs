package com.hwzn.pojo.dto.courseTerm;

import cn.hutool.json.JSONArray;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author： hy
 * @Date： 2025/7/17 14:04
 * @Desc： 获取课程
 */
@Data
public class FilterCourseTermListDto {

	private Integer pageNum=1;

	private Integer pageSize=9999;

	private JSONArray sortArray;

	private String name;

	private Integer status;

	@NotNull(message = "课程ID不能为空")
	private Integer courseId;

	private String startTime;

	private String endTime;
}