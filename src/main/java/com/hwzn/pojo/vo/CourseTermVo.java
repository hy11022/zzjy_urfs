package com.hwzn.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: hy
 * @CreateTime: 2025-7-21 17:01
 * @Description: 课程期次
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseTermVo implements Serializable {

    private static final long serialVersionUID = -7085727211578749152L;

    private Integer id;

    private String name;

    private String startTime;

    private String endTime;

}