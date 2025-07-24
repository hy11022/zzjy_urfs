package com.hwzn.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:38
 * @Desc: 用户日志实体类
 */
@Data
@TableName("user_logs")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLogEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	
	private String des;
	
	private String createTime;

	private String createrAccount;

	@TableField(exist = false)//不属于表字段
	private String createrName;
}
