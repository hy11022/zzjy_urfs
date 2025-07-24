package com.hwzn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.activity.GetActivityListByPageDto;
import com.hwzn.pojo.entity.ActivityEntity;
import java.util.List;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:37
 * @Desc: 路由服务接口
 */
public interface ActivityService {

    void createActivity(ActivityEntity activityEntity);

    ActivityEntity getActivityById(Integer id);

    void updateActivity(ActivityEntity activityEntity);

    void updateActivityStatus(ActivityEntity activityEntity);

    void deleteActivity(Integer id);

    IPage<ActivityEntity> getActivityListByPage(GetActivityListByPageDto getActivityListByPageDto);

    ActivityEntity getActivityInfoByID(Integer id);

}