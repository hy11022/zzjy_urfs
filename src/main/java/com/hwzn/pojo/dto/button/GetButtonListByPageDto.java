package com.hwzn.pojo.dto.button;

import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/23 17:07
 * @Desc: 获取按钮列表
 */
@Data
public class GetButtonListByPageDto {

	@NotNull(message = "页数不能为空")
	private Integer pageNum;

	@NotNull(message = "页码不能为空")
	private Integer pageSize;

	private Integer routerId;
	
	private String name;

	private String code;

}
