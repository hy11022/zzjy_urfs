package com.hwzn.pojo.dto.course;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author： Du Rongjun
 * @Date： 2023/9/26 15:26
 * @Desc： 获取课程教师列表（分页）
 */
@Data
public class GetCourseTeacherListByPageDto {

	@NotNull(message = "页数不能为空")
	private Integer pageNum;

	@NotNull(message = "页码不能为空")
	private Integer pageSize;

	private Integer courseId;

	private String teacherAccount;

	private Integer role;
}