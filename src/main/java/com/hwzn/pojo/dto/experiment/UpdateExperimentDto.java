package com.hwzn.pojo.dto.experiment;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author：Rongjun Du
 * @Date：2023/11/29 11:12
 * @Desc：更新实验
 */
@Data
public class UpdateExperimentDto {
	
	@NotNull(message = "ID不能为空")
	private Integer id;
	
	@NotBlank(message = "名称不能为空")
	@Length(max = 30,message = "名称长度不能超过30位")
	private String name;

	@NotBlank(message = "网络路径不能为空")
	@Length(max = 500,message = "网络路径长度不能超过500位")
	private String url;
	
}
