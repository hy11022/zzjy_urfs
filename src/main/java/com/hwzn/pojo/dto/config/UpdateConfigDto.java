package com.hwzn.pojo.dto.config;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.Length;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/29 16:55
 * @Desc: 创建用户
 */
@Data
public class UpdateConfigDto {

	@NotNull(message = "id不能为空")
	private Integer id;

	@NotBlank(message = "名称不能为空")
	@Length(max = 20,message = "名称长度不能超过20位")
	private String name;

	@NotBlank(message = "编码不能为空")
	@Length(max = 100,message = "编码长度不能超过100位")
	@Pattern(regexp = "^[^\\u4e00-\\u9fa5]*$",message = "编码不能含有中文")
	private String code;

	@Range(min=1,max = 2,message = "类别只能为1和2")
	private Integer type;

	@NotBlank(message = "内容不能为空")
	private String content;

	private String des;
}
