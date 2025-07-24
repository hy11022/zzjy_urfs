package com.hwzn.pojo.dto.course;

import cn.hutool.json.JSONArray;
import lombok.Data;

/**
 * @Author： Du Rongjun
 * @Date： 2023/9/26 15:26
 * @Desc： 获取课程
 */
@Data
public class GetCourseListByPageDto {

	private Integer pageNum=1;

	private Integer pageSize=9999;

	private JSONArray sortArray;

	private String name;

	private String author;

	private String organization;

	private String des;

	private String chargerAccount;

	private String account;
}