package com.hwzn.pojo.dto.dbTbLog;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import cn.hutool.json.JSONArray;
import lombok.Data;

/**
 * @Author： Du Rongjun
 * @Date： 2023/8/23 17:07
 * @Desc： 用户登录
*/
@Data
public class GetDbTbLogListByPageDto {

	@NotNull(message = "页数不能为空")
	private Integer pageNum;

	@NotNull(message = "页码不能为空")
	private Integer pageSize;

	private String dbName;
	
	private String tbName;

	@JsonProperty("rId")//需要指定
	private Integer rId;

	private String des;

	private String createrAccount;

	private JSONArray sortArray;
}