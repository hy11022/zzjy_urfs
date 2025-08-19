package com.hwzn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.client.FilterClientDto;
import com.hwzn.pojo.entity.ClientEntity;
import java.util.List;

/**
 * @Author: hy
 * @Date: 2025/8/6 10:43
 * @Desc: 终端管理
 */
public interface ClientService {

    IPage<ClientEntity> filterClientList(FilterClientDto filterClientDto);

    List<ClientEntity> getRecordByIp(String ip);

    Integer recordOnline(ClientEntity clientEntity);

    Integer updateOnlineTime(ClientEntity clientEntity);
}