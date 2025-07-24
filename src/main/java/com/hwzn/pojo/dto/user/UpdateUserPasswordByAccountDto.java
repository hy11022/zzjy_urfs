package com.hwzn.pojo.dto.user;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/29 16:55
 * @Desc: 创建用户
 */
@Data
public class UpdateUserPasswordByAccountDto {

	@NotBlank(message = "密码不能为空")
//	@Pattern(regexp ="(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[^a-zA-Z0-9]).{8,20}",message = "用密码格式有误，新密码必须包含大小写字母、数字、特殊字符，8-20位")
	private String password;
}