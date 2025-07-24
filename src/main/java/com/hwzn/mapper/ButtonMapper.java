package com.hwzn.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import com.hwzn.pojo.entity.ButtonEntity;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:42
 * @Desc: 用户日志
 */
@SuppressWarnings("ALL")
@Mapper
@Repository
public interface ButtonMapper extends BaseMapper<ButtonEntity> {

    @Select("SELECT buttons.*,routers.title AS routerName" +
            " FROM buttons LEFT JOIN routers ON buttons.router_id = routers.id" +
            " ${ew.customSqlSegment} ")
    IPage<ButtonEntity> selectListPage(Page<ButtonEntity> page, @Param("ew") Wrapper<ButtonEntity> wrapper);
}
