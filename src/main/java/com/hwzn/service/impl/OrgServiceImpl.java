package com.hwzn.service.impl;

import org.springframework.stereotype.Service;
import com.hwzn.pojo.entity.OrgEntity;
import com.hwzn.service.OrgService;
import com.hwzn.mapper.OrgMapper;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:41
 * @Desc: 组织
 */
@Service
public class OrgServiceImpl implements OrgService {
	
	@Resource
	private OrgMapper orgMapper;

	@Override
	public List<OrgEntity> getOrgList() {
		return orgMapper.selectList(null);
	}

	@Override
	public Integer updateOrg(OrgEntity orgEntity) {
		return orgMapper.updateById(orgEntity);
	}

	@Override
	public OrgEntity getOrgInfoById(Integer id) {
		return orgMapper.selectById(id);
	}
}