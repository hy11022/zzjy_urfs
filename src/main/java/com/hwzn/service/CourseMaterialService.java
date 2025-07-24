package com.hwzn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.courseMaterial.FilterCourseMaterialListDto;
import com.hwzn.pojo.entity.CourseMaterialsEntity;
import java.util.List;

/**
 * @Author: hy
 * @Date: 2025/7/18 17:26
 * @Desc: 课程学员
 */
public interface CourseMaterialService {

    IPage<CourseMaterialsEntity> filterCourseMaterialList(FilterCourseMaterialListDto filterCourseMaterialListDto);

    Integer createCourseMaterial(CourseMaterialsEntity courseMaterialsEntity);

    Integer deleteCourseMaterial(Integer id);

    CourseMaterialsEntity getCourseMaterialById(Integer id);

    Integer updateCourseMaterial(CourseMaterialsEntity courseMaterialsEntity);

    List<CourseMaterialsEntity> getCourseMaterialByCourse(Integer courseId);

}