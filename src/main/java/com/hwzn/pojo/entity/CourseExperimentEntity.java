package com.hwzn.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Author：hy
 * @Date: 2025/7/24 10:52
 * @Desc: 课程实验实体
 */
@Data
@TableName("course_experiments")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseExperimentEntity {
	
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	private Integer courseId;

	private String name;

	private String des;

	private String cover;

	private String video;

	private String projectName;

	private String projectIntro;

	private String teacherIntro;

	private String experimentGuide;

	private String assessmentRequirement;

	private Integer experimentContentType;//1我的虚仿实验验2虚仿资源库3虚仿实验链接4线下实验任务

	private String experimentContent;

	private String learnResource;

	private String experimentClass;

	private String experimentStartTime;

	private String experimentEndTime;

	private Integer allowRepeat;

	private Integer allowLate;

	private Integer scoreRate;

	private Integer reportMethod;//报告批阅形式，1教师批阅2自动成绩3自动成绩+教师批阅

	private Integer teacherRate;

	private Integer allowTrain;

	@TableField(exist = false)//不属于表字段,实训时长
	private String courseName;
}