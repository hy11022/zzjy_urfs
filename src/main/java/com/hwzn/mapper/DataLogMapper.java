package com.hwzn.mapper;

import com.hwzn.pojo.entity.DataLogEntity;
import org.apache.ibatis.annotations.Param;
import com.hwzn.pojo.vo.FilterDataLogListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @Author：Rongjun Du
 * @Date：2023/10/11 10:46
 * @Desc：数据日志
 */
@Mapper
@Repository
public interface DataLogMapper extends EasyBaseMapper<DataLogEntity> {
	
	@Select("SELECT data_logs.*,users.name AS createrName FROM data_logs " +
			"LEFT JOIN users ON users.account = data_logs.creater_account " +
			"${ew.customSqlSegment}")
	IPage<FilterDataLogListVo> filterList(Page<FilterDataLogListVo> page, @Param("ew") QueryWrapper<FilterDataLogListVo> queryWrapper);
}