package com.hwzn.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author：Rongjun Du
 * @Date：2023/11/14 14:21
 * @Desc：数据日志实体
 */
@Data
@TableName("data_logs")
public class DataLogEntity {
	
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	
	private String createrAccount;
	
	private String createTime;
	
	private Integer type;
	
	private String tableName;
	
	private Integer dataId;
	
	private String content;
}