package com.hwzn.pojo.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hy
 * @since 2023-5-9
 */
@Data
public class CreateUserByBatchDto {

    @NotNull(message = "部门不能为空")
    private Integer deptId;

    @NotBlank(message = "部门树路径不能为空")
    private String deptIds;

    @NotBlank(message = "角色不能为空")
    private String roleIds;

    @NotBlank(message = "账号文件不能为空")
    private String filePath;
}