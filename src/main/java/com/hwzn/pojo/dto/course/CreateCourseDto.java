package com.hwzn.pojo.dto.course;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

/**
 * @Author: hy
 * @Date: 2025/7/16 10:50
 * @Desc: 创建课程
 */
@Data
public class CreateCourseDto {

	@NotBlank(message = "课程名称不能为空")
	@Length(max = 20,message = "课程名称长度不能超过20位")
	private String name;

	@NotBlank(message = "作者不能为空")
	@Length(max = 10,message = "作者长度不能超过10位")
	private String author;

	@NotBlank(message = "组织单位不能为空")
	@Length(max = 20,message = "组织单位长度不能超过20位")
	private String organization;

	private String des;

	@NotBlank(message = "负责人账户不能为空")
	@Length(max = 20,message = "负责人账户长度不能超过20位")
	private String chargerAccount;

	private String cover;

	private String video;

	private String assistants;
}