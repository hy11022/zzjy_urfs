package com.hwzn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.Collection;

/**
 * @Description: EasyBaseMapper 扩展通用 Mapper，支持数据批量插入
 * @Author: WangYejian
 */
public interface EasyBaseMapper<T> extends BaseMapper<T> {

    /**
     * 批量插入 仅适用于mysql
     * @param entityList 实体列表
     */
    void insertBatchSomeColumn(Collection<T> entityList);

}