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
 * @CreateTime: 2025-7-25 16:57
 * @Description: 学习记录
 */
@Data
@TableName("study_records")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudyRecordsEntity implements Serializable {

    private static final long serialVersionUID = -7085727211578749152L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String createrAccount;

    private String createTime;

    private Integer type;

    private Integer dataId;

    private String content;

    private String detail;

    @TableField(exist = false)//不属于表字段
    private String createrName;

    @TableField(exist = false)//不属于表字段
    private String className;
}