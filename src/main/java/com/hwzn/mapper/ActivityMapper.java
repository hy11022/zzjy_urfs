package com.hwzn.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.hwzn.pojo.entity.ActivityEntity;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:42
 * @Desc: 横幅
 */
@Mapper
@Repository
public interface ActivityMapper extends BaseMapper<ActivityEntity> {

    @Select("SELECT id,title,author,cover,intro,read_count,submit_time,status FROM activitys ${ew.customSqlSegment} ")
    IPage<ActivityEntity> selectListPage(Page<ActivityEntity> page, @Param("ew") QueryWrapper<ActivityEntity> queryWrapper);

    @Update("UPDATE activitys SET submit_time=#{submitTime},status=#{status} WHERE id=#{id}")
    int updateActivityStatus(ActivityEntity activityEntity);

}