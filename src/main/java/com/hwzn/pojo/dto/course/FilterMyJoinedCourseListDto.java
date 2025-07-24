package com.hwzn.pojo.dto.course;

import cn.hutool.json.JSONArray;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author： hy
 * @Date： 2025/7/22 16:42
 * @Desc： 筛选我参加过的课程列表
 */
@Data
public class FilterMyJoinedCourseListDto {

	private Integer pageNum=1;

	private Integer pageSize=9999;

	private JSONArray sortArray;

	private String name;

	private String author;

	private String organization;

	private String account;
}