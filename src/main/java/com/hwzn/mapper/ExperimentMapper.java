package com.hwzn.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwzn.pojo.entity.ExperimentEntity;
import com.hwzn.pojo.vo.ExperimentInfoVo;
import com.hwzn.pojo.vo.FilterExperimentListVo;
import com.hwzn.pojo.vo.GetClientExperimentListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @Author：Rongjun Du
 * @Date：2023/11/29 10:50
 * @Desc：实验
 */
@Mapper
@Repository
public interface ExperimentMapper extends BaseMapper<ExperimentEntity> {
	
	@Select("SELECT experiments.*, users.name AS createrName " +
			"FROM experiments " +
			"LEFT JOIN users ON experiments.creater_account = users.account " +
			"${ew.customSqlSegment} ")
	IPage<ExperimentEntity> filterList(Page<ExperimentEntity> page, @Param("ew") QueryWrapper<ExperimentEntity> queryWrapper);
	
	@Select("SELECT experiments.*,projects.name AS projectName " +
			"FROM experiments " +
			"LEFT JOIN projects ON projects.id = experiments.project_id " +
			"WHERE experiments.id = #{id}")
	ExperimentInfoVo getInfoById(Integer id);
}