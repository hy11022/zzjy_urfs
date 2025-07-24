package com.hwzn.pojo.dto.button;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/29 16:55
 * @Desc: 创建按钮
 */
@Data
public class UpdateButtonDto {

	@NotNull(message = "id不能为空")
	private Integer id;

	@NotNull(message = "路由ID不能为空")
	private Integer routerId;

	@NotBlank(message = "名称不能为空")
	@Length(max = 20,message = "名称长度不能超过20位")
	private String name;

	@NotBlank(message = "编码不能为空")
	@Length(max = 255,message = "编码长度不能超过255位")
	@Pattern(regexp = "^[^\\u4e00-\\u9fa5]*$",message = "编码不能含有中文")
	private String code;
}
