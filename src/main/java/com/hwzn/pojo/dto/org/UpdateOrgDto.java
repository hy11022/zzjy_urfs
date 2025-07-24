package com.hwzn.pojo.dto.org;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/23 9:44
 * @Desc:
 */
@Data
public class UpdateOrgDto {

	@NotNull(message = "id不能为空")
	private Integer id;

	@NotBlank(message = "名称不能为空")
	private String name;
	
	@NotBlank(message = "LOGO不能为空")
	private String logo;
	
	private String address;
	
}
