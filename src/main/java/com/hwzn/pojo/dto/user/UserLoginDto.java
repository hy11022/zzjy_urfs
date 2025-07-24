package com.hwzn.pojo.dto.user;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/23 17:07
 * @Desc: 用户登录
 */
@Data
public class UserLoginDto {
	
	@NotBlank(message = "账户不能为空")
	private String account;
	
	@NotBlank(message = "密码不能为空")
	private String password;
	
}
