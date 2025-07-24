package com.hwzn.pojo.dto.common;

import lombok.Data;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:36
 * @Desc: 描述
 */
@Data
public class StatusDto {
	
	@NotNull(message = "id不能为空")
	private Integer id;

	@NotNull(message = "状态不能为空")
	@Range(min = 0, max = 1, message = "状态只能是0和1")
	private Integer status;
}
