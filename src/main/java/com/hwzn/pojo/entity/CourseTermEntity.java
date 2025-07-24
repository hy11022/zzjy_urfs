package com.hwzn.pojo.entity;

import lombok.Data;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @Author: hy
 * @CreateTime: 2025-7-17 13:58
 * @Description: 课程期次
 */
@Data
@TableName("course_terms")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseTermEntity implements Serializable {

    private static final long serialVersionUID = -7085727211578749152L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer courseId;

    private String name;

    private String startTime;

    private String endTime;

    private Integer status;
}