package com.hwzn.pojo.dto.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/29 16:55
 * @Desc: 创建用户
 */
@Data
public class CreateUserDto {

	private String classCode;

	@NotBlank(message = "账户不能为空")
	@Length(max = 20,message = "账户长度不能超过20位")
	private String account;
	
	@NotBlank(message = "名称不能为空")
	@Length(max = 10,message = "名称长度不能超过10位")
	private String name;

	@NotNull(message = "角色不能为空")
	private Integer role;
}