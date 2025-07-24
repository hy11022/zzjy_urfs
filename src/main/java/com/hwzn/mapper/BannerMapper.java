package com.hwzn.mapper;

import com.hwzn.pojo.entity.BannerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:42
 * @Desc: 横幅
 */
@Mapper
@Repository
public interface BannerMapper extends BaseMapper<BannerEntity> {

}
