package com.hwzn.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.io.Serializable;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/29 16:55
 * @Desc: 创建用户
 */
@Data
@TableName("buttons")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ButtonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	private Integer routerId;

	@TableField(exist = false)//不属于表字段
	private String routerName;

	private String name;

	@TableField(exist = false)//不属于表字段
	private String title;

	private String code;

	@TableField(exist = false)//不属于表字段
	private Integer type;

	public String getTitle() {
		return name;
	}
}