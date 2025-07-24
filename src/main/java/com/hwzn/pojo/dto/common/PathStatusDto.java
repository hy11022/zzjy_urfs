package com.hwzn.pojo.dto.common;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/4 15:26
 * @Desc: 路径状态
 */
@Data
public class PathStatusDto {
	
	@NotBlank(message = "路径不能为空")
	private String path;
	
	@NotNull(message = "状态不能为空")
	@Range(min = 0,max = 1,message = "状态只能为0和1")
	private Integer status;
}
