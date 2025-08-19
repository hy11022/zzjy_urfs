package com.hwzn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hwzn.pojo.entity.ClientEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: hy
 * @Date: 2025/8/6 10:42
 * @Desc: 终端管理
 */
@SuppressWarnings("ALL")
@Mapper
@Repository
public interface ClientMapper extends BaseMapper<ClientEntity> {

}