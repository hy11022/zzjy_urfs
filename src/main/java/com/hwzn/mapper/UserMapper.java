package com.hwzn.mapper;

import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.vo.listTeacherVo;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import com.hwzn.pojo.entity.UserEntity;
import java.util.List;

/**
 * @Author: Du Rongjun
 * @Date: 2023/8/30 10:04
 * @Desc: 用户
 */
@SuppressWarnings("ALL")
@Mapper
@Repository
public interface UserMapper extends EasyBaseMapper<UserEntity> {

    @Select("SELECT users.id,users.class_code,users.account,users.name,users.role,classes.name AS className" +
            " FROM users LEFT JOIN classes ON users.class_code = classes.code" +
            " ${ew.customSqlSegment} ")
    IPage<UserEntity> selectListPage(Page<UserEntity> page, @Param("ew") Wrapper<UserEntity> wrapper);

    @Select("SELECT users.*,classes.name AS className" +
            " FROM users LEFT JOIN classes ON users.class_code = classes.code" +
            " ${ew.customSqlSegment} ")
    List<UserEntity> getUserInfoByAccount(@Param("ew") QueryWrapper<UserEntity> queryWrapper);

    @Select("SELECT users.*" +
            " FROM users LEFT JOIN classes ON users.class_code = classes.code" +
            " ${ew.customSqlSegment} ")
    List<UserEntity> countByClassId(@Param("ew") QueryWrapper<UserEntity> queryWrapper);

    @Select("SELECT account,name from users" +
            " ${ew.customSqlSegment} ")
    IPage<listTeacherVo> listTeacher(Page<listTeacherVo> page, @Param("ew") Wrapper<listTeacherVo> wrapper);

    @Select("SELECT account,name FROM users WHERE FIND_IN_SET(account,#{assistants})")
    List<UserEntity> getAssisListByAccounts(String assistants);
}