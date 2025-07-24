package com.hwzn.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.hwzn.pojo.entity.DbTbLogEntity;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/4 17:26
 * @Desc: 数据库数据表日志
 */
@SuppressWarnings("ALL")
@Mapper
@Repository
public interface DbTbLogMapper extends BaseMapper<DbTbLogEntity> {

    @Select("SELECT db_tb_logs.*,users.name AS createrName" +
            " FROM db_tb_logs LEFT JOIN users ON db_tb_logs.creater_account = users.account" +
            " ${ew.customSqlSegment} ")
    IPage<DbTbLogEntity> selectListPage(Page<DbTbLogEntity> page, @Param("ew") Wrapper<DbTbLogEntity> wrapper);
}
