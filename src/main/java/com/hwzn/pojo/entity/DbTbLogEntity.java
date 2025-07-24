package com.hwzn.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/4 17:13
 * @Desc: 数据库数据表日志实现类
 */
@Data
@TableName("db_tb_logs")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DbTbLogEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	
	private String dbName;
	
	private String tbName;
	
	private Integer rId;
	
	private String des;
	
	private String createTime;
	
	private String createrAccount;

	private String createrName;
}
