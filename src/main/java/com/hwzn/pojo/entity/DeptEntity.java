package com.hwzn.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:38
 * @Desc: 组织表
 */
@Data
@TableName("depts")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeptEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	
	private String name;
	
	private Integer parId;
	
	private Integer level;

	private Integer seq;

	@TableField(exist = false)//不属于表字段
	private List<DeptEntity> children;
}
