package com.hwzn.service;

import com.hwzn.pojo.dto.banner.GetBannerListByPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.entity.BannerEntity;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:37
 * @Desc: 路由服务接口
 */
public interface BannerService {

    void createBanner(BannerEntity bannerEntity);

    IPage<BannerEntity> getBannerListByPage(GetBannerListByPageDto getBannerListByPageDto);

    void updateBanner(BannerEntity bannerEntity);

    BannerEntity getBannerById(Integer id);

    void deleteBanner(Integer id);
}