package com.hwzn.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/4 13:56
 * @Desc: 上传记录实体类
 */
@Data
@TableName("upload_records")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UploadRecordEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	
	private String path;
	
	private String createTime;
	
	private Integer status;
}
