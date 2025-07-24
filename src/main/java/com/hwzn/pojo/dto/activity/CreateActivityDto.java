package com.hwzn.pojo.dto.activity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/29 16:55
 * @Desc: 创建用户
 */
@Data
public class CreateActivityDto {

	@NotBlank(message = "标题不能为空")
	@Length(max = 50,message = "标题长度不能超过50位")
	private String title;

	@NotBlank(message = "作者不能为空")
	@Length(max = 20,message = "作者长度不能超过20位")
	private String author;

	private String cover;

	private String intro;

	@NotBlank(message = "内容不能为空")
	private String content;
}