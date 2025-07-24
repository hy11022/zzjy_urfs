package com.hwzn.pojo.vo;

import lombok.Data;
import java.io.Serializable;

/**
 * @Author: hy
 * @Date: 2025/7/16 11:29
 * @Desc: 罗列教师
 */
@Data
public class listTeacherVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String account;

    private String name;
}