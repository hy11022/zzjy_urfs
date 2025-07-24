package com.hwzn.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hwzn.pojo.dto.router.CreateRouterDto;
import com.hwzn.pojo.dto.router.UpdateRouterDto;
import com.hwzn.pojo.entity.RouterEntity;
import com.hwzn.pojo.Result;
import java.util.List;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:37
 * @Desc: 路由服务接口
 */
public interface RouterService {

    void createRouter(RouterEntity routerEntity);

    RouterEntity getRouterById(Integer id);

    List<RouterEntity> getRouterByParId(Integer id);

    List<RouterEntity> getRouterList();

    List<RouterEntity> selectRouterList(QueryWrapper<RouterEntity> routerQueryWrapper);

    void updateRouter(RouterEntity routerEntity);

    Result validateUpdateParam(UpdateRouterDto updateRouterDto);

    Result validateCreateParam(CreateRouterDto createRouterDto);

    Result validateDeleteParam(RouterEntity routerEntity);

    void deleteRouter(Integer id);

}