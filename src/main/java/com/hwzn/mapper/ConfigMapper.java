package com.hwzn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;
import com.hwzn.pojo.entity.ConfigsEntity;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:42
 * @Desc: 用户日志
 */
@Mapper
@Repository
public interface ConfigMapper extends BaseMapper<ConfigsEntity> {

}
