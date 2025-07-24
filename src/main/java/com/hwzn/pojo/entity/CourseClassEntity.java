package com.hwzn.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: hy
 * @CreateTime: 2025-7-18 17:21
 * @Description: 课程班级
 */
@Data
@TableName("course_classes")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseClassEntity implements Serializable {

    private static final long serialVersionUID = -7085727211578749152L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer courseId;

    private Integer termId;

    private String code;

    private String chargerAccount;

    private String createTime;

    @TableField(exist = false)//不属于表字段
    private String name;

    @TableField(exist = false)//不属于表字段
    private String chargerName;

}