package com.hwzn.mapper;

import com.hwzn.pojo.entity.OrgEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:42
 * @Desc: 用户日志
 */
@Mapper
@Repository
public interface OrgMapper extends BaseMapper<OrgEntity> {

}
