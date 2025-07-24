package com.hwzn.pojo.dto.course;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/26 15:50
 * @Desc: 创建课程教师
 */
@Data
public class CreateCourseTeacherDto {

	@NotNull(message = "课程不能为空")
	private Integer courseId;

	@NotBlank(message = "教师账户不能为空")
	@Length(max = 20,message = "教师账户长度不能超过20位")
	private String teacherAccount;
}