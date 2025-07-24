package com.hwzn.pojo.dto.loginLog;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/30 14:00
 * @Desc: 创建登录日志
 */
@Data
public class CreateLoginLogDto {
	
	@NotBlank(message = "IP不能为空")
	@Length(max = 20,message = "IP长度不能超过20位")
	private String ip;
	
	@Length(max = 20,message = "经度长度不能超过20位")
	private String lgt;
	
	@Length(max = 20,message = "维度长度不能超过20位")
	private String lat;
	
	@Length(max = 50,message = "地址长度不能超过50位")
	private String address;
	
	@Length(max = 20,message = "设备长度不能超过20位")
	private String device;
	
	@Length(max = 20,message = "操作系统长度不能超过20位")
	private String os;
	
	@Length(max = 50,message = "操作系统版本长度不能超过50位")
	private String osVersion;
	
	@Length(max = 20,message = "浏览器长度不能超过20位")
	private String browser;
	
	@Length(max = 50,message = "浏览器版本长度不能超过50位")
	private String browserVersion;
	
}
