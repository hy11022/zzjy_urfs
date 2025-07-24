package com.hwzn.pojo.dto.courseClass;

import cn.hutool.json.JSONArray;
import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * @Author： hy
 * @Date： 2025/7/21 8:54
 * @Desc： 获取课程班级
 */
@Data
public class FilterCourseClassListDto {

	private Integer pageNum=1;

	private Integer pageSize=9999;

	private JSONArray sortArray;

	private String startTime;

	private String endTime;

	@NotNull(message = "课程ID不能为空")
	private Integer courseId;

	@NotNull(message = "期次ID不能为空")
	private Integer termId;

	private String name;

	private String chargerAccount;
}