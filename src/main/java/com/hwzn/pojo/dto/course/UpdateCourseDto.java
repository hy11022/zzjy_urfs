package com.hwzn.pojo.dto.course;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/29 16:55
 * @Desc: 更新活动
 */
@Data
public class UpdateCourseDto {

	@NotNull(message = "id不能为空")
	private Integer id;

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