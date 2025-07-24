package com.hwzn.pojo.dto.experiment;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author：Rongjun Du
 * @Date：2023/11/29 11:12
 * @Desc：创建实验
 */
@Data
public class CreateExperimentDto {
	
	@NotBlank(message = "名称不能为空")
	@Length(max = 30,message = "名称长度不能超过30位")
	private String name;
	
	@NotBlank(message = "网络路径不能为空")
	@Length(max = 500,message = "网络路径长度不能超过500位")
	private String url;
	
	private String createrAccount;

}
