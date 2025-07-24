package com.hwzn.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import cn.hutool.json.JSONObject;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/25 15:03
 * @Desc: 返回格式统一
 */
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
	
	private Integer code;
	
	private String msg;
	
	private JSONObject data;
	
	private Long timeStamp;
	
	public Result() {

	}

	/**
	 * 请求成功，对返回结果进行封装
	 */
	public static Result showInfo(Integer code, String msg, JSONObject data) {
		return build(code,msg,data,System.currentTimeMillis());
	}
	
	/**
	 * 返回结果构造
	 */
	private static Result build(Integer code, String msg, JSONObject data, Long timeStamp){
		return Result.builder()
				.code(code)
				.msg(msg)
				.data(data)
				.timeStamp(timeStamp)
				.build();
	}
}
