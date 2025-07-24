package com.hwzn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hwzn.pojo.entity.ClassEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author：Rongjun Du
 * @Date：2024/2/20 16:35
 * @Desc：班级
 */
@Mapper
@Repository
public interface ClassMapper extends BaseMapper<ClassEntity> {

}
