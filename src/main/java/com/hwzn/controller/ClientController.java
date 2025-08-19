package com.hwzn.controller;

import cn.hutool.core.date.DateTime;
import com.hwzn.pojo.dto.client.HeartbeatDto;
import com.hwzn.service.*;
import com.hwzn.pojo.Result;
import com.hwzn.pojo.entity.*;
import cn.hutool.json.JSONUtil;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import com.hwzn.pojo.dto.client.FilterClientDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.util.CommonUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @Author: hy
 * @Date: 2025/8/6 10:32
 * @Desc: 终端管理
 */
@RestController
@RequestMapping("/client")
public class ClientController {

	@Resource
	ClientService clientService;

	//筛选终端列表（管理员）
	@PostMapping("/admin/filterClientList")
	public Result filterClientList(@Validated @RequestBody FilterClientDto filterClientDto){
		IPage<ClientEntity> resultList = clientService.filterClientList(filterClientDto);
		return Result.showInfo(0,"Success", JSONUtil.parseObj(resultList));
	}

	//客户端账户心跳（定时调用）
	@PostMapping("/heartbeat")
	public Result heartbeat(@Validated @RequestBody HeartbeatDto heartbeatDto, HttpServletRequest request){
		String ip = CommonUtil.getClientIpByRequest(request);
		List<ClientEntity> clientList = clientService.getRecordByIp(ip);
		if(!clientList.isEmpty() && clientList.get(0).getStatus()==0){
			ClientEntity clientEntity = new ClientEntity();
			clientEntity.setId(clientList.get(0).getId());
			clientEntity.setIp(ip);
			clientEntity.setLastOntime(DateTime.now().toString());
			clientEntity.setStatus(1);
			clientEntity.setProcessor(heartbeatDto.getCpu()+"位");
			clientService.updateOnlineTime(clientEntity);
		}
		return Result.showInfo(0,"Success",null);
	}
}