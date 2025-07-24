package com.hwzn.pojo.dto.dept;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/29 16:55
 * @Desc: 创建用户
 */
@Data
public class CreateDeptDto {
	
	@NotBlank(message = "名称不能为空")
	@Length(max = 20,message = "名称长度不能超过20位")
	private String name;
	
	@NotNull(message = "父级ID不能为空")
	@Min(value=0,message = "父级ID不能小于0")
	private Integer parId;

	@NotNull(message = "层级不能为空")
	@Min(value=1,message = "层级不能小于1")
	private Integer level;

	@NotNull(message = "排序不能为空")
	@Min(value=1,message = "排序不能小于1")
	private Integer seq;
}
