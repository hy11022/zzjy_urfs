package com.hwzn.pojo.dto.courseExperiment;

import javax.validation.constraints.NotNull;
import cn.hutool.json.JSONArray;
import lombok.Data;

@Data
public class FilterCourseExperimentListDto {

    private Integer pageNum=1;

    private Integer pageSize=9999;

    private JSONArray sortArray;

    @NotNull(message = "课程ID不能为空")
    private Integer courseId;

    private String name;

//    private String projectName;
//
//    private Integer allowTrain;
}