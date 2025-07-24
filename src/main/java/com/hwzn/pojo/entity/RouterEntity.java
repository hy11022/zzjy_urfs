package com.hwzn.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import cn.hutool.json.JSONObject;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/29 16:55
 * @Desc: 创建用户
 */
@Data
@TableName("routers")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouterEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@TableField(exist = false)//不属于表字段
	private Integer code;

	private Integer level;

	private Integer parId;

	private Integer type;

	private String title;

	private String name;

	private String path;

	private Integer seq;

	@TableField(exist = false)//不属于表字段
	private JSONObject meta;

	@TableField(exist = false)//不属于表字段
	private List children;

	public JSONObject getMeta() {
		JSONObject jo = new JSONObject();
		jo.put("title",title);
		return jo;
	}

	public Integer getCode() {
		return id;
	}
}
