package com.hwzn.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Author：Rongjun Du
 * @Date：2023/11/28 11:19
 * @Desc：实验实体
 */
@Data
@TableName("experiments")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExperimentEntity {
	
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	
	private String name;
	
	private String url;
	
	private String createrAccount;
	
	private String createTime;
	
	@TableField(exist = false)//不属于表字段
	private String createrName;
}