package com.hwzn.pojo.dto.classes;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @Author：Rongjun Du
 * @Date：2024/2/20 16:56
 * @Desc：创建班级
 */
@Data
public class CreateClassDto {
	
	@NotBlank(message = "名称不能为空")
	@Length(max = 20,message = "名称长度不能超过20位")
	private String name;
	
}
