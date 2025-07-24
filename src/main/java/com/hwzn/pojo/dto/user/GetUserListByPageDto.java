package com.hwzn.pojo.dto.user;

import cn.hutool.json.JSONArray;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author： Du Rongjun
 * @Date： 2023/8/23 17:07
 * @Desc： 用户登录
 */
@Data
public class GetUserListByPageDto {

	private Integer pageNum=1;

	private Integer pageSize=9999;

	private JSONArray sortArray;

	private String classCode;

	private String account;
	
	private String name;

	private Integer role;

}
