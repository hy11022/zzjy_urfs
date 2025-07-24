package com.hwzn.pojo.dto.role;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/23 9:44
 * @Desc:
 */
@Data
public class UpdateRoleDto {

	@NotNull(message = "id不能为空")
	private Integer id;

	@NotBlank(message = "名称不能为空")
	private String name;
	
	@NotBlank(message = "路由权限不能为空")
	private String routerAuthority;

	@NotBlank(message = "按钮权限不能为空")
	private String buttonAuthority;
	
}
