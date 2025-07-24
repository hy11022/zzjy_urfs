package com.hwzn.pojo.dto.course;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/23 17:07
 * @Desc: 获取课程类别列表
 */
@Data
public class GetCourseTypeListByPageDto {

	@NotNull(message = "页数不能为空")
	private Integer pageNum;

	@NotNull(message = "页码不能为空")
	private Integer pageSize;

	private String name;

	private Integer status;
}