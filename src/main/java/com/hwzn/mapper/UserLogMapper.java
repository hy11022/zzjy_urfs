package com.hwzn.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.vo.FilterUserLogListVo;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import com.hwzn.pojo.entity.UserLogEntity;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:42
 * @Desc: 用户日志
 */
@SuppressWarnings("ALL")
@Mapper
@Repository
public interface UserLogMapper extends BaseMapper<UserLogEntity> {
    @Select("SELECT user_logs.*,users.name AS createrName FROM user_logs " +
            " LEFT JOIN users ON user_logs.creater_account = users.account" +
            " ${ew.customSqlSegment}")
    IPage<UserLogEntity> selectListPage(Page<UserLogEntity> page, @Param("ew") QueryWrapper<UserLogEntity> queryWrapper);

    @Select("SELECT user_logs.*,users.name AS createrName FROM user_logs " +
            "LEFT JOIN users ON users.account = user_logs.creater_account " +
            " ${ew.customSqlSegment}")
    IPage<FilterUserLogListVo> filterList(Page<FilterUserLogListVo> page, @Param("ew") QueryWrapper<FilterUserLogListVo> queryWrapper);
}