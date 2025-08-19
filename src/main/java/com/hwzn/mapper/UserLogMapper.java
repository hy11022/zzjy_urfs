package com.hwzn.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.pojo.entity.UserLogEntity;
import com.hwzn.pojo.vo.FilterUserLogListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Author：Rongjun Du
 * @Date：2023/10/11 9:09
 * @Desc：用户日志
 */
@Mapper
@Repository
public interface UserLogMapper extends BaseMapper<UserLogEntity> {
	
	@Select("SELECT user_logs.*,users.name AS createrName FROM user_logs " +
			"LEFT JOIN users ON users.account = user_logs.creater_account " +
			" ${ew.customSqlSegment}")
	IPage<FilterUserLogListVo> filterList(Page<FilterUserLogListVo> page, @Param("ew") QueryWrapper<FilterUserLogListVo> queryWrapper);
}
