package com.hwzn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.pojo.dto.banner.GetBannerListByPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.hwzn.pojo.entity.BannerEntity;
import com.hwzn.service.BannerService;
import com.hwzn.mapper.BannerMapper;
import cn.hutool.core.util.StrUtil;
import javax.annotation.Resource;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:41
 * @Desc: 横幅
 */
@Service
public class BannerServiceImpl implements BannerService {
	
	@Resource
	private BannerMapper bannerMapper;

	@Override
	public void createBanner(BannerEntity bannerEntity) {
		bannerMapper.insert(bannerEntity);
	}

	@Override
	public IPage<BannerEntity> getBannerListByPage(GetBannerListByPageDto getBannerListByPageDto) {
		Page<BannerEntity> page = new Page<>(getBannerListByPageDto.getPageNum(),getBannerListByPageDto.getPageSize());//分页
		BannerEntity bannerEntity = new BannerEntity();
		BeanUtils.copyProperties(getBannerListByPageDto,bannerEntity);
		QueryWrapper<BannerEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(getBannerListByPageDto.getStatus() != null,"status",getBannerListByPageDto.getStatus())
				.like(StrUtil.isNotBlank(getBannerListByPageDto.getName()),"name",getBannerListByPageDto.getName());
		return bannerMapper.selectPage(page,queryWrapper);
	}

	@Override
	public void updateBanner(BannerEntity bannerEntity) {
		bannerMapper.updateById(bannerEntity);
	}

	@Override
	public BannerEntity getBannerById(Integer id) {
		return bannerMapper.selectById(id);
	}

	@Override
	public void deleteBanner(Integer id) {
		bannerMapper.deleteById(id);
	}
}
