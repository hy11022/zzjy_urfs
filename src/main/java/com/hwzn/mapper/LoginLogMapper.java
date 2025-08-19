package com.hwzn.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.pojo.entity.LoginLogEntity;
import com.hwzn.pojo.vo.FilterLoginLogListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Author：Rongjun Du
 * @Date：2023/10/10 16:40
 * @Desc：登录日志
 */
@Mapper
@Repository
public interface LoginLogMapper extends BaseMapper<LoginLogEntity> {
	
	@Select("SELECT login_logs.*,users.name AS createrName FROM login_logs " +
			"LEFT JOIN users ON users.account = login_logs.creater_account " +
			"${ew.customSqlSegment}")
	IPage<FilterLoginLogListVo> filterList(Page<FilterLoginLogListVo> page, @Param("ew") QueryWrapper<FilterLoginLogListVo> queryWrapper);
	
}
