package com.hwzn.pojo.dto.courseTerm;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @Author: hy
 * @Date: 2025/7/17 15:30
 * @Desc: 更新课程期次
 */
@Data
public class UpdateCourseTermDto {

	@NotNull(message = "id不能为空")
	private Integer id;

	@NotBlank(message = "课程名称不能为空")
	@Length(max = 10,message = "课程名称长度不能超过10位")
	private String name;

	@NotBlank(message = "课程期次开始时间不能为空")
	private String startTime;

	@NotBlank(message = "课程期次结束时间不能为空")
	private String endTime;
}