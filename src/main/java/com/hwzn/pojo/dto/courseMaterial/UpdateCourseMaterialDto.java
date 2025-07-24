package com.hwzn.pojo.dto.courseMaterial;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @Author: hy
 * @Date: 2025/7/22 15:15
 * @Desc: 更新课程资料
 */
@Data
public class UpdateCourseMaterialDto {

	@NotNull(message = "id不能为空")
	private Integer id;

	@NotBlank(message = "课程名称不能为空")
	@Length(max = 20,message = "课程名称长度不能超过20位")
	private String name;

	@NotBlank(message = "网络路径不能为空")
	private String url;

	private String createrAccount;
}