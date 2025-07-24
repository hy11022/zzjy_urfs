package com.hwzn.pojo.dto.courseClass;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: hy
 * @Date: 2025/7/21 09:07
 * @Desc: 创建课程
 */
@Data
public class AddCourseClassDto {

	@NotNull(message = "课程不能为空")
	private Integer courseId;

	@NotNull(message = "期次不能为空")
	private Integer termId;

	private String chargerAccount;

	@NotBlank(message = "班级不能为空")
	private String code;

}