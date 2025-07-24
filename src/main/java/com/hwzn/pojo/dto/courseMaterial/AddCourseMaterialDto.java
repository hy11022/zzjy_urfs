package com.hwzn.pojo.dto.courseMaterial;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: hy
 * @Date: 2025/7/22 13:18
 * @Desc: 创建课程
 */
@Data
public class AddCourseMaterialDto {

	@NotNull(message = "课程不能为空")
	private Integer courseId;

	@Length(max = 20,message = "名称长度不能超过20位")
	@NotBlank(message = "名称不能为空")
	private String name;

	@NotBlank(message = "网络路径不能为空")
	private String url;
}