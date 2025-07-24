package com.hwzn.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author：Rongjun Du
 * @Date：2024/2/20 16:29
 * @Desc：班级实体类
 */
@Data
@TableName("classes")
public class ClassEntity {
	
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	private String code;

	private String name;
}