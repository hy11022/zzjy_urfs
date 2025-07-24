package com.hwzn.pojo.dto.experiment;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: hy
 * @Date: 2025/07/24 13:02
 * @Desc: 更新课程实验
 */
@Data
public class UpdateCourseExperimentDto {
	
	@NotNull(message = "ID不能为空")
	private Integer id;

	@NotBlank(message = "名称不能为空")
	private String name;

	private String des;

	private String cover;

	private String video;

	private String projectName;

	private String projectIntro;

	private String teacherIntro;

	private String experimentGuide;

	private String assessmentRequirement;

	@NotNull(message = "实验内容类型不能为空")
	private Integer experimentContentType;

	private String experimentContent;

	private String learnResource;

	private String experimentClass;

	@NotBlank(message = "课程实验开始时间不能为空")
	private String experimentStartTime;

	@NotBlank(message = "课程实验结束时间不能为空")
	private String experimentEndTime;

	@NotNull(message = "是否允许重复实验不能为空")
	private Integer allowRepeat;

	@NotNull(message = "是否允许迟交不能为空")
	private Integer allowLate;

	@NotNull(message = "报告生成方式不能为空")
	private Integer reportMethod;

	private Integer allowTrain;

}
