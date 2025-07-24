package com.hwzn.pojo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:38
 * @Desc: 组织表
 */
@Data
@TableName("orgs")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrgEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	
	private String name;
	
	private String logo;
	
	private String address;
}
