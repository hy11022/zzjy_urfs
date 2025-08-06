package com.hwzn.pojo.dto.courseExperiment;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: hy
 * @Date: 2025/07/24 13:02
 * @Desc: 更新课程实验成绩占比列表
 */
@Data
public class UpdateCourseExperimentRateDto {

	@NotNull(message = "占比列表不能为空")
	private List rateList;

}
