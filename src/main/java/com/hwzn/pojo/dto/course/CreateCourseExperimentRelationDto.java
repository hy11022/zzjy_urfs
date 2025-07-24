package com.hwzn.pojo.dto.course;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/26 15:50
 * @Desc: 创建课程实验关联
 */
@Data
public class CreateCourseExperimentRelationDto {

	@NotNull(message = "类别不能为空")
	private Integer courseId;

	@NotNull(message = "封面不能为空")
	private Integer experimentId;
}