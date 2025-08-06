package com.hwzn.pojo.vo;

import cn.hutool.json.JSONArray;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hwzn.pojo.entity.CourseExperimentEntity;
import com.hwzn.pojo.entity.CourseMaterialsEntity;
import com.hwzn.pojo.entity.UserEntity;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: hy
 * @CreateTime: 2025-7-21 16:45
 * @Description: 课程
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseInfoVo implements Serializable {

    private static final long serialVersionUID = -7085727211578749152L;

    private Integer id;

    private String name;

    private String author;

    private String organization;

    private String des;

    private String chargerAccount;

    private String cover;

    private String video;

    private String assistants;

    private String chargerName;

    private List<UserEntity> assistantList;

    private Object currentTermInfo;

    private List<CourseMaterialsEntity> materialList;

    private List<CourseExperimentEntity> experimentList;

    private boolean isJoined;
}