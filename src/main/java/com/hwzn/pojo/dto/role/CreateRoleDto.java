package com.hwzn.pojo.dto.role;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/29 16:55
 * @Desc: 创建用户
 */
@Data
public class CreateRoleDto {

	@NotBlank(message = "名称不能为空")
	@Length(max = 20,message = "名称长度不能超过20位")
	private String name;

	@NotBlank(message = "路由权限不能为空")
	private String routerAuthority;

	@NotBlank(message = "按钮权限不能为空")
	private String buttonAuthority;
}