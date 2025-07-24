package com.hwzn.pojo.dto.upload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/25 15:54
 * @Desc: Base64上传
 */
@Data
public class UploadByBase64Dto {

	@NotBlank(message = "base64编码不能为空")
	private String base64;
	
	@NotBlank(message = "类型不能为空")
	private String type;
}
