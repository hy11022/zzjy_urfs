package com.hwzn.pojo.dto.courseTerm;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @Author: hy
 * @Date: 2025/7/17 14:32
 * @Desc: 创建课程
 */
@Data
public class CreateCourseTermDto {

	@NotBlank(message = "课程期次名称不能为空")
	@Length(max = 10,message = "课程期次名称长度不能超过10位")
	private String name;

	@NotNull(message = "课程不能为空")
	private Integer courseId;

	@NotBlank(message = "课程期次开始时间不能为空")
	private String startTime;

	@NotBlank(message = "课程期次结束时间不能为空")
	private String endTime;
}