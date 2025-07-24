package com.hwzn.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/29 16:55
 * @Desc: 创建用户
 */
@Data
@TableName("activitys")
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivityEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	private String title;

	private String author;

	private String cover;

	private String intro;

	private String content;

	private Integer readCount;

	private String submitTime;

	private Integer status;

}