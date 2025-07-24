package com.hwzn.pojo.dto.course;

import cn.hutool.json.JSONArray;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author： hy
 * @Date： 2025/7/17 9:11
 * @Desc： 筛选我负责的课程列表
 */
@Data
public class FilterMyChargingCourseListDto {

	private Integer pageNum=1;

	private Integer pageSize=9999;

	private JSONArray sortArray;

	private String account;

	private String name;

	private String des;

}