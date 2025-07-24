package com.hwzn.pojo.dto.common;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author：Rongjun Du
 * @Date：2024/6/4 17:46
 * @Desc：
 */
@Data
public class CreateFolderDto {
	
	@NotBlank(message = "路径不能为空")
	private String path;
	
	@NotBlank(message = "名称不能为空")
	private String name;
	
}
