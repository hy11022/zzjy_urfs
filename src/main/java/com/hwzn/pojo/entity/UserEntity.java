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
 * @Date: 2023/8/23 16:44
 * @Desc: 用户
 */
@Data
@TableName("users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	private String classCode;

	private String account;

	private String name;

	private Integer role;

	private String password;

	@TableField(exist = false)//不属于表字段
	private String className;

	@TableField(exist = false)//不属于表字段
	private Boolean isNeedUpdatePassword;
}