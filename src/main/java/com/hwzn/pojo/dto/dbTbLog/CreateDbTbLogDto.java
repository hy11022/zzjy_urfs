package com.hwzn.pojo.dto.dbTbLog;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/4 17:23
 * @Desc: 创建数据库数据表日志
 */
@Data
public class CreateDbTbLogDto {
	
	@NotBlank(message = "数据库名称不能为空")
	@Length(max = 20,message = "数据库名称长度不能超过20位")
	private String dbName;
	
	@NotBlank(message = "数据表名称不能为空")
	@Length(max = 20,message = "数据表名称长度不能超过20位")
	private String tbName;
	
	@NotNull(message = "记录ID不能为空")
	@JsonProperty("rId")//需要指定
	private Integer rId;

	@NotBlank(message = "描述不能为空")
	private String des;
}
