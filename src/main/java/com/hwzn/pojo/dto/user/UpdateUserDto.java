package com.hwzn.pojo.dto.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/29 16:55
 * @Desc: 创建用户
 */
@Data
public class UpdateUserDto {

	@NotNull(message = "id不能为空")
	private Integer id;

	@NotBlank(message = "班级编码不能为空")
	private String classCode;

	@NotBlank(message = "姓名不能为空")
	@Length(max = 10,message = "名称长度不能超过10位")
	private String name;
}