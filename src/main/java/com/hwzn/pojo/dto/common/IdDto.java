package com.hwzn.pojo.dto.common;

import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:36
 * @Desc: 描述
 */
@Data
public class IdDto {
	
	@NotNull(message = "id不能为空")
	private Integer id;
}
