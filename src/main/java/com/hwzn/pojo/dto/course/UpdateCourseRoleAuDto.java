package com.hwzn.pojo.dto.course;

import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @Author: hy
 * @Date: 2025/7/18 15:02
 * @Desc: 更新课程角色权限
 */
@Data
public class UpdateCourseRoleAuDto {

	@NotNull(message = "id不能为空")
	private Integer id;

	private String assistantAuthority;

	private String classTeacherAuthority;
}