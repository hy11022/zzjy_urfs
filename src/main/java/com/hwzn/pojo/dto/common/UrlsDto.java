package com.hwzn.pojo.dto.common;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/4 11:44
 * @Desc: 路径
 */
@Data
public class UrlsDto {
	
	@NotBlank(message = "路径不能为空")
	private String urls;
}
