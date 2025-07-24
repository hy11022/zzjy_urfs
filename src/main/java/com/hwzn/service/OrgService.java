package com.hwzn.service;

import com.hwzn.pojo.entity.OrgEntity;
import java.util.List;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:37
 * @Desc: 组织服务接口
 */
public interface OrgService {

    List<OrgEntity> getOrgList();

    Integer updateOrg(OrgEntity orgEntity);

    OrgEntity getOrgInfoById(Integer id);
}
