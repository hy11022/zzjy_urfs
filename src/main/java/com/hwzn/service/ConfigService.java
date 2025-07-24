package com.hwzn.service;

import com.hwzn.pojo.dto.config.GetConfigListByPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.entity.ConfigsEntity;
import com.hwzn.pojo.Result;
import java.util.List;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:37
 * @Desc: 路由服务接口
 */
public interface ConfigService {

    Integer createConfig(ConfigsEntity configsEntity);

    Integer updateConfig(ConfigsEntity configsEntity);

    Result validateUpdateParam(Integer id);

    void deleteConfig(Integer id);

    IPage<ConfigsEntity> getButtonList(GetConfigListByPageDto getConfigListByPageDto);

    String getConfigContentByCode(String code);

    ConfigsEntity getConfigInfoByCode(String code);

    ConfigsEntity getConfigsInfoByID(Integer id);
}