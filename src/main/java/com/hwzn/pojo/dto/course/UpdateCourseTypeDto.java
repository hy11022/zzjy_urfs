package com.hwzn.pojo.dto.course;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hy
 * @since 2023-5-9
 */
@Data
public class UpdateCourseTypeDto {

    @NotNull(message = "id不能为空")
    private Integer id;

    @NotBlank(message = "名称不能为空")
    @Length(max = 20,message = "名称长度不能超过20位")
    private String name;

    @NotNull(message = "排序不能为空")
    private Integer seq;
}