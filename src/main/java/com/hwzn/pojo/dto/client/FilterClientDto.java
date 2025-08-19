package com.hwzn.pojo.dto.client;

import cn.hutool.json.JSONArray;
import lombok.Data;

/**
 * @Author： hy
 * @Date： 2025/8/6 10:39
 * @Desc： 终端管理
 */
@Data
public class FilterClientDto {

	private Integer pageNum=1;

	private Integer pageSize=9999;

	private JSONArray sortArray;

	private String ip;

	private String os;

	private Integer status;

	private String processor;

	private String codeVersion;

	private Integer fireWallStatus;

	private Integer outbreakPreventionStatus;
}