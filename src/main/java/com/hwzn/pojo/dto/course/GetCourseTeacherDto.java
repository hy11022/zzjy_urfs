package com.hwzn.pojo.dto.course;

import lombok.Data;

/**
 * @Author： Du Rongjun
 * @Date： 2023/9/26 15:26
 * @Desc： 获取课程教师列表
 */
@Data
public class GetCourseTeacherDto {

	private Integer courseId;

	private String teacherAccount;

	private Integer role;

}