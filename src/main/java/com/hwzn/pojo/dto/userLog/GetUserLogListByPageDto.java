package com.hwzn.pojo.dto.userLog;

import javax.validation.constraints.NotNull;
import cn.hutool.json.JSONArray;
import lombok.Data;

/**
 * @Author： Du Rongjun
 * @Date： 2023/8/23 17:07
 * @Desc： 用户登录
 */
@Data
public class GetUserLogListByPageDto {

	@NotNull(message = "页数不能为空")
	private Integer pageNum;

	@NotNull(message = "页码不能为空")
	private Integer pageSize;

	private JSONArray sortArray;

	private String des;
	
	private String createrAccount;

	private String startTime;

	private String endTime;
}
