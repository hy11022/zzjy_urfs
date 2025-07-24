package com.hwzn.service;

import com.hwzn.pojo.dto.dept.CreateDeptDto;
import com.hwzn.pojo.dto.dept.UpdateDeptDto;
import com.hwzn.pojo.entity.DeptEntity;
import com.hwzn.pojo.Result;
import java.util.List;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:37
 * @Desc: 部门服务接口
 */
public interface DeptService {

    Integer createDept(DeptEntity deptEntity);

    List<DeptEntity> getDeptList();

    Integer updateDept(DeptEntity deptEntity);

    DeptEntity getDeptById(Integer id);

    List<DeptEntity> getDeptListByParId(Integer id);

    Integer deleteDept(Integer id);

    Result validateCreateParam(CreateDeptDto createDeptDto);

    Result validateUpdateParam(UpdateDeptDto updateDeptDto);

    Result validateDeleteParam(DeptEntity deptEntity1);
}