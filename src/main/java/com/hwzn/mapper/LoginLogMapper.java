package com.hwzn.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.vo.FilterLoginLogListVo;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.hwzn.pojo.entity.LoginLogEntity;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/29 10:13
 * @Desc: 登录日志
 */
@SuppressWarnings("ALL")
@Mapper
@Repository
public interface LoginLogMapper extends BaseMapper<LoginLogEntity> {
    @Select("SELECT login_logs.*,users.name AS createrName FROM login_logs " +
            " LEFT JOIN users ON login_logs.creater_account = users.account" +
            " ${ew.customSqlSegment}")
    IPage<LoginLogEntity> selectListPage(Page<LoginLogEntity> page, @Param("ew") QueryWrapper<LoginLogEntity> queryWrapper);

    @Select("SELECT login_logs.*,users.name AS createrName FROM login_logs " +
            "LEFT JOIN users ON users.account = login_logs.creater_account " +
            "${ew.customSqlSegment}")
    IPage<FilterLoginLogListVo> filterList(Page<FilterLoginLogListVo> page, @Param("ew") QueryWrapper<FilterLoginLogListVo> queryWrapper);

}