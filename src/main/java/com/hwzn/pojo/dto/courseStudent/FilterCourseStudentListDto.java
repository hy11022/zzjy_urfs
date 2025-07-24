package com.hwzn.pojo.dto.courseStudent;

import cn.hutool.json.JSONArray;
import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * @Author： hy
 * @Date： 2025/7/17 14:04
 * @Desc： 获取课程
 */
@Data
public class FilterCourseStudentListDto {

	private Integer pageNum=1;

	private Integer pageSize=9999;

	private JSONArray sortArray;

	private String startTime;

	private String endTime;

	@NotNull(message = "课程ID不能为空")
	private Integer courseId;

	@NotNull(message = "期次ID不能为空")
	private Integer termId;

	private String account;

	private String name;

	private String classCode;

	private Integer joinMethod;//1教师添加2自主学习
}