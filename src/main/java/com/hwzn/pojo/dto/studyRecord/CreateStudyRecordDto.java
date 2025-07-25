package com.hwzn.pojo.dto.studyRecord;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: hy
 * @Date: 2025/7/25 16:59
 * @Desc: 学习记录
 */
@Data
public class CreateStudyRecordDto {

	@NotNull(message = "类型不能为空")
	private Integer type;

	@NotNull(message = "数据ID不能为空")
	private Integer dataId;

	@NotBlank(message = "内容不能为空")
	private String content;

	private String detail;
}