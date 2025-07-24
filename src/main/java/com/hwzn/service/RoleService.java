package com.hwzn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.role.GetRoleListByPageDto;
import javax.servlet.http.HttpServletRequest;
import com.hwzn.pojo.entity.RoleEntity;
import java.util.List;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:37
 * @Desc: 路由服务接口
 */
public interface RoleService {

    void createRole(RoleEntity roleEntity);

    IPage<RoleEntity> getRoleListByPage(GetRoleListByPageDto getRoleListByPageDto);

    List<RoleEntity> getRoleList();

    void updateRole(RoleEntity roleEntity);

    RoleEntity getRoleById(Integer id);

    void deleteRole(Integer id);

    List<Object> getRoleByButtonAuthority(String buttonAuthority);

    List<RoleEntity> getAuthorityByAccount(List roleIdList);

    Boolean hasAuthosity(HttpServletRequest request);

}