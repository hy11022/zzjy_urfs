package com.hwzn.pojo.dto.courseStudent;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: hy
 * @Date: 2025/7/17 14:32
 * @Desc: 创建课程
 */
@Data
public class AddCourseStudentDto {

	@NotNull(message = "课程不能为空")
	private Integer courseId;

	@NotNull(message = "期次不能为空")
	private Integer termId;

	private String account;

}